package ureca.nolmung.implementation.recommend.batch;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@EnableScheduling
@RequiredArgsConstructor
public class BatchScheduler {

    private final JobLauncher jobLauncher;
    private final Job saveRecommendationsJob;

//    @Scheduled(initialDelay = 5000, fixedDelay = 5000)
//    @Scheduled(cron = "0 0 3 * * ?") // 실제 사용한다하면 이거로
    public void runBatchJob() {
        try {
            jobLauncher.run(saveRecommendationsJob, new org.springframework.batch.core.JobParametersBuilder()
                    .addLong("timestamp", System.currentTimeMillis())
                    .toJobParameters());
        } catch (Exception e) {
            //TODO 공통 예외처리 필요
            e.printStackTrace();
        }
    }
}
