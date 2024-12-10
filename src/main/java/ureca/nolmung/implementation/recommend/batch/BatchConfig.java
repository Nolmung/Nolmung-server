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
import org.springframework.transaction.PlatformTransactionManager;
import software.amazon.awssdk.services.personalizeruntime.model.PredictedItem;
import ureca.nolmung.business.recommend.dto.response.RecommendResp;
import ureca.nolmung.implementation.recommend.AwsPersonalizeManager;
import ureca.nolmung.implementation.recommend.RedisManager;
import ureca.nolmung.implementation.recommend.dtomapper.RecommendDtoMapper;
import ureca.nolmung.implementation.user.UserManager;
import ureca.nolmung.jpa.place.Place;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.util.AbstractMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@Slf4j
@Configuration
@EnableBatchProcessing
@RequiredArgsConstructor
public class BatchConfig {

    private final DataSource dataSource;
    private final JobRepository jobRepository;
    private final PlatformTransactionManager platformTransactionManager;
    private final UserManager userManager;
    private final AwsPersonalizeManager awsPersonalizeManager;
    private final RedisManager redisManager;
    private final RecommendDtoMapper recommendDtoMapper;

    private static final int TIME_TO_LIVE = 48;

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
                .writer(combinedWriter())
                .build();
    }

    @Bean
    public JdbcCursorItemReader<Long> userJdbcReader() {
        JdbcCursorItemReader<Long> reader = new JdbcCursorItemReader<>();
        reader.setDataSource(dataSource);

        reader.setSql(
                "SELECT u.user_id " +
                        "FROM user u " +
                        "LEFT JOIN user_bookmark ub " +
                        "ON u.user_id = ub.user_id " +
                        "WHERE ABS(COALESCE(ub.bookmark_count, 0) - u.bookmark_count) >= 10"
        );

        reader.setRowMapper((ResultSet rs, int rowNum) -> rs.getLong("user_id"));
        return reader;
    }


    @Bean
    public ItemProcessor<Long, Map.Entry<Long, List<RecommendResp>>> recommendationProcessor() {
        return userId -> {
            List<PredictedItem> awsRecs = awsPersonalizeManager.getRecs(userId);
            List<Place> places = awsPersonalizeManager.getPlaces(awsRecs);
            List<RecommendResp> recommendResponses = recommendDtoMapper.toGetPlaceRecommendations(places);
            return new AbstractMap.SimpleEntry<>(userId, recommendResponses);
        };
    }

    @Bean
    public ItemWriter<Map.Entry<Long, List<RecommendResp>>> combinedWriter() {
        return items -> items.forEach(entry -> {
            Long userId = entry.getKey();
            List<RecommendResp> recommendResps = entry.getValue();

            redisManager.boundValueOps(String.valueOf(userId), recommendResps, TIME_TO_LIVE, TimeUnit.HOURS);

            userManager.updateBookmarkCount(userId);
        });
    }
}