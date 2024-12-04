package ureca.nolmung.implementation.recommend.batch;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.database.JdbcCursorItemReader;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.transaction.PlatformTransactionManager;
import ureca.nolmung.business.recommend.RecommendUseCase;
import ureca.nolmung.business.recommend.dto.response.RecommendResp;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.AbstractMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Configuration
@EnableBatchProcessing
@RequiredArgsConstructor
public class BatchConfig {

    private final JobRepository jobRepository;
    private final PlatformTransactionManager platformTransactionManager;
    private final DataSource dataSource;
    private final RecommendUseCase recommendUseCase;
    private final RedisTemplate<String, Object> redisTemplate;

    @Bean
    public Job saveRecommendationsJob() {
        return new JobBuilder("saveRecommendationsJob", jobRepository)
                .incrementer(new RunIdIncrementer())
                .start(saveRecommendationsStep())
                .build();
    }

    @Bean
    public Step saveRecommendationsStep() {
        return new StepBuilder("saveRecommendationsStep", jobRepository)
                .<Long, Map.Entry<Long, List<RecommendResp>>>chunk(100, platformTransactionManager)
                .reader(userJdbcReader())
                .processor(recommendationProcessor())
                .writer(redisWriter())
                .build();
    }

    @Bean
    public JdbcCursorItemReader<Long> userJdbcReader() {
        JdbcCursorItemReader<Long> reader = new JdbcCursorItemReader<>();
        reader.setDataSource(dataSource);
        reader.setSql("SELECT user_id FROM user");
        reader.setRowMapper(new RowMapper<Long>() {
            @Override
            public Long mapRow(ResultSet rs, int rowNum) throws SQLException {
                return rs.getLong("user_id");
            }
        });
        return reader;
    }

    @Bean
    public ItemProcessor<Long, Map.Entry<Long, List<RecommendResp>>> recommendationProcessor() {
        return userId -> {
            List<RecommendResp> recommendations = recommendUseCase.getPlaceRecommendationsFromPersonalizeForBatch(userId);
            return new AbstractMap.SimpleEntry<>(userId, recommendations);
        };
    }

    @Bean
    public ItemWriter<Map.Entry<Long, List<RecommendResp>>> redisWriter() {
        return items -> {
            for (Map.Entry<Long, List<RecommendResp>> entry : items) {
                Long userId = entry.getKey();
                List<RecommendResp> recommendations = entry.getValue();
                redisTemplate.opsForValue().set(String.valueOf(userId), recommendations);
            }
        };
    }
}