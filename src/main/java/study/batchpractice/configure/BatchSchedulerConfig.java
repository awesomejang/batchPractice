package study.batchpractice.configure;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.TemporalAdjusters;

@Slf4j
@Configuration
@EnableScheduling
@RequiredArgsConstructor
public class BatchSchedulerConfig {

    private final JobLauncher jobLauncher;
    private final Job createLottoJob;

    @Scheduled(cron = "*/3 * * * * *")
    public void runBatchJob() throws Exception {
        log.info(">>>>> JOB SCHEDULER START");
        JobParameters time = new JobParametersBuilder()
                .addString("time", String.valueOf(LocalDateTime.now()))
                .toJobParameters();
        try {
            jobLauncher.run(createLottoJob, time);
        } catch (Exception e) {
            log.error(">>>>> Error in runBatchJob: {}", e.getMessage());
            throw e;
        }
        log.info(">>>>> JOB SCHEDULER END");
    }
}
