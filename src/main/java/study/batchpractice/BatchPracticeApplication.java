package study.batchpractice;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import java.time.LocalDateTime;

@SpringBootApplication
@EnableJpaAuditing
@EnableBatchProcessing
public class BatchPracticeApplication implements CommandLineRunner
{
    @Autowired
    private JobLauncher jobLauncher;

    @Autowired
    private Job createTotalLottoJob;

    @Autowired
    private Job createLottoJob;

    public static void main(String[] args) {
        SpringApplication.run(BatchPracticeApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        JobParameters params = new JobParametersBuilder().addString("time", String.valueOf(LocalDateTime.now())).toJobParameters();
        jobLauncher.run(createTotalLottoJob, params);
    }
}
