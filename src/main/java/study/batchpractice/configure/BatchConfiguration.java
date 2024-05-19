package study.batchpractice.configure;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
//import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;

import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.JobScope;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.PlatformTransactionManager;
import study.batchpractice.Tasks.LottoCountCheckTasklet;

@Slf4j
@Configuration // deprecated to 5.x
@RequiredArgsConstructor
public class BatchConfiguration {

    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;
    private final LottoCountCheckTasklet lottoCountCheckTasklet;
    private final JobLauncher jobLauncher;

//    public void runJob() throws Exception {
//        JobParameters jobParameters = new JobParametersBuilder().addLong("time", System.currentTimeMillis()).toJobParameters();
//        jobLauncher.run(simpleJob(), jobParameters);
//    }

    @Bean
    public Job simpleJob() {
        return jobBuilderFactory.get("createLottoNumbersJob")
                .start(lottoTargetDateCountCheck(null))
                .listener(new CustomJobExecutionListener())
                .build();
    }

    @Bean
    @JobScope
    public Step lottoTargetDateCountCheck(@Value("#{jobParameters['time']}") String time) {
        log.debug("time -> {}", time);
        return stepBuilderFactory.get("lottoTargetDateCountCheck").tasklet(lottoCountCheckTasklet).build();
    }


//    @Autowired @Deprecated
//    private final JobBuilderFactory jobBuilderFactory;

//    @Bean
//    public Job simpleJob(JobRepository jobRepository, Tasklet testTasklet, PlatformTransactionManager platformTransactionManager) {
//        return new JobBuilder("simpleJob", jobRepository).start(simpleStep1(jobRepository, testTasklet, platformTransactionManager)).build();
//    }
//
//    @Bean
//    public Step simpleStep1(JobRepository jobRepository, Tasklet testTasklet, PlatformTransactionManager platformTransactionManager){
//        return new StepBuilder("simpleStep1", jobRepository)
//                .tasklet(testTasklet, platformTransactionManager).build();
//    }
//    @Bean
//    public Tasklet testTasklet(){
//        return ((contribution, chunkContext) -> {
//            log.info(">>>>> This is Step1");
//            return RepeatStatus.FINISHED;
//        });
//    }
}
