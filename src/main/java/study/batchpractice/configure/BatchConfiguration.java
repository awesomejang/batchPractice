package study.batchpractice.configure;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
//import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;

import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.JobScope;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.PlatformTransactionManager;

@Slf4j
@Configuration
@EnableBatchProcessing // deprecated to 5.x
@RequiredArgsConstructor
public class BatchConfiguration {

    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;

    @Bean
    public Job simpleJob() {
        return jobBuilderFactory.get("simpleJob")
                .start(simpleStep1())
                .build();
    }

    @Bean
    @JobScope // batch 실행 동안에만 생성
    public Step simpleStep1() {
        return stepBuilderFactory.get("simpleStep1")
                .tasklet((contribution, chunkContext) -> {
                    log.info(">>>>> This is Step1");
//                    log.info(">>>>> requestDate = {}", requestDate);
                    return RepeatStatus.FINISHED;
                })
                .build();
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
