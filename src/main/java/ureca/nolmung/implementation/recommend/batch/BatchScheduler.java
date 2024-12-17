package ureca.nolmung.implementation.recommend.batch;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@EnableScheduling
@RequiredArgsConstructor
public class BatchScheduler {

    private final JobLauncher jobLauncher;
    private final Job saveRecommendationsJob;
    private final Job exportCsvToS3Job;

//    @Scheduled(initialDelay = 5000, fixedDelay = 5000)
    @Scheduled(cron = "0 0 3 * * ?") // 실제 사용한다하면 이거로
    public void runBatchJob() {
        try {
            jobLauncher.run(saveRecommendationsJob, new org.springframework.batch.core.JobParametersBuilder()
                    .addLong("timestamp", System.currentTimeMillis())
                    .toJobParameters());
        } catch (Exception e) {
            log.warn("레디스에 캐싱하는 배치 작업 중 오류 발생: {}", e.getMessage());
        }
    }

    @Scheduled(cron = "0 0 0 */7 * *")
    public void runExportCsvToS3Job() {
        try {
            jobLauncher.run(exportCsvToS3Job, new org.springframework.batch.core.JobParameters());
        } catch (Exception e) {
            log.warn("S3에 파일 올리는 배치 작업 중 오류 발생: {}", e.getMessage());
        }
    }
}