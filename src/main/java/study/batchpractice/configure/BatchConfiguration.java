package study.batchpractice.configure;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.JobScope;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import study.batchpractice.Tasks.CreateLottoNumberTasklet;
import study.batchpractice.Tasks.CreateLottoTasklet;
import study.batchpractice.Tasks.LottoCountCheckTasklet;

@Slf4j
@Configuration // deprecated to 5.x
@RequiredArgsConstructor
public class BatchConfiguration {

    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;
    private final LottoCountCheckTasklet lottoCountCheckTasklet;
    private final CreateLottoNumberTasklet createLottoNumberTasklet;
    private final CreateLottoTasklet createLottoTasklet;
    private final JobLauncher jobLauncher;

//    public void runJob() throws Exception {
//        JobParameters jobParameters = new JobParametersBuilder().addLong("time", System.currentTimeMillis()).toJobParameters();
//        jobLauncher.run(simpleJob(), jobParameters);
//    }

    @Bean
    public Job simpleJob() {
        return jobBuilderFactory.get("createLottoNumbersJob")
                .preventRestart()
                .start(lottoTargetDateCountCheck(null))
                .next(createLottoNumberStep())
                .next(createLottoStep())
                .listener(new CustomJobExecutionListener())
                .build();
    }

    @Bean
    @JobScope
    public Step lottoTargetDateCountCheck(
            @Value("#{jobParameters['time']}") String time
    ) {
        log.debug("time -> {}", time);
        return stepBuilderFactory.get("lottoTargetDateCountCheck").tasklet(lottoCountCheckTasklet).build();
    }

    @Bean
    public Step createLottoNumberStep() {
        return stepBuilderFactory.get("createLottoNumberStep").tasklet(createLottoNumberTasklet).build();
    }

    @Bean
    public Step createLottoStep() {
        return stepBuilderFactory.get("createLottoStep").tasklet(createLottoTasklet).build();
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
