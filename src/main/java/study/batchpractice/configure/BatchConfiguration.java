package study.batchpractice.configure;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.JobScope;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.job.builder.FlowBuilder;
import org.springframework.batch.core.job.flow.Flow;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.database.JpaPagingItemReader;
import org.springframework.batch.item.database.builder.JpaPagingItemReaderBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.Transactional;
import study.batchpractice.Tasks.CreateLottoNumberTasklet;
import study.batchpractice.Tasks.CreateLottoTasklet;
import study.batchpractice.Tasks.LottoCountCheckTasklet;
import study.batchpractice.entities.LottoEntity;
import study.batchpractice.processor.LottoItemProcessor;
import study.batchpractice.repository.LottoRepository;
import study.batchpractice.repository.TotalLottoRepository;
import study.batchpractice.writer.TotalLottoItemWriter;

import javax.persistence.EntityManagerFactory;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;
import java.util.Map;


@Slf4j
@Configuration // deprecated to 5.x
@RequiredArgsConstructor
public class BatchConfiguration {
    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;
    private final LottoCountCheckTasklet lottoCountCheckTasklet;
    private final CreateLottoNumberTasklet createLottoNumberTasklet;
    private final CreateLottoTasklet createLottoTasklet;
//    private final JpaPagingItemReader<LottoEntity> lottoJpaPagingItemReader;

    private final EntityManagerFactory entityManagerFactory;

    private final LottoRepository lottoRepository;
    private final TotalLottoRepository totalLottoRepository;
    private final PlatformTransactionManager transactionManager;

//    public void runJob() throws Exception {
//        JobParameters jobParameters = new JobParametersBuilder().addLong("time", System.currentTimeMillis()).toJobParameters();
//        jobLauncher.run(simpleJob(), jobParameters);
//    }

//    @Bean
//    public JpaCursorItemReader<LottoEntity> lottoItemReader() {
//        return new LottoItemReader(lottoRepository, LocalDate.now().with(TemporalAdjusters.nextOrSame(DayOfWeek.SATURDAY)));
//    }

    // TODO: DTO, Map등으로 파라미터 여러개 전달 가능한지 확인
    @Bean(name = "lottoJpaPagingItemReader")
    public JpaPagingItemReader<LottoEntity> createJpaPagingItemReader() {
        return new JpaPagingItemReaderBuilder<LottoEntity>()
                .name("lottoJpaPagingItemReader")
                .entityManagerFactory(entityManagerFactory)
                .pageSize(10)
                .queryString("SELECT le FROM LottoEntity le where le.targetDate = :targetDate")
                .parameterValues(Map.of("targetDate", LocalDate.now().with(TemporalAdjusters.nextOrSame(DayOfWeek.SATURDAY))))
                .build();
    }

    @Bean(name = "createTotalLottoJob")
    @Transactional
    public Job createTotalLottoJob() {
        return jobBuilderFactory.get("createTotalLottoJob")
                .incrementer(new RunIdIncrementer())
                .flow(totalLottoCreateStep())
                .end()
                .build();
    }

    @Bean
    public Step totalLottoCreateStep() {
        return stepBuilderFactory.get("totalLottoCreateStep")
                .<LottoEntity, String>chunk(10)
//                .reader(new LottoItemReader(lottoRepository, LocalDate.now().with(TemporalAdjusters.nextOrSame(DayOfWeek.SATURDAY))))
                .reader(createJpaPagingItemReader())
                .processor(new LottoItemProcessor(LocalDate.now().with(TemporalAdjusters.nextOrSame(DayOfWeek.SATURDAY))))
                .writer(new TotalLottoItemWriter(totalLottoRepository))
                .build();
    }

    @Bean(name = "createLottoJob")
    public Job createLottoJob() {
        return jobBuilderFactory.get("createLottoNumbersJob")

//                .preventRestart()
                .start(createLottoFlow())

                .end()
                .listener(new CustomJobExecutionListener())
                .build();
    }

    @Bean
    public Flow createLottoFlow() {
        return new FlowBuilder<Flow>("createLottoFlow")
                .start(lottoTargetDateCountCheck(null, transactionManager))
                .on("COUNT_OVER_10").end()
                .from(lottoTargetDateCountCheck(null, transactionManager))
                .on("*").to(createLottoNumberStep(transactionManager))
                .next(createLottoStep(transactionManager))
                .build();
    }

    @Bean
    @JobScope
    public Step lottoTargetDateCountCheck(
            @Value("#{jobParameters['time']}") String time,
            PlatformTransactionManager transactionManager
    ) {
        log.debug("time -> {}", time);
        return stepBuilderFactory.get("lottoTargetDateCountCheck")
                .tasklet(lottoCountCheckTasklet)
                .transactionManager(transactionManager)
                .build();
    }

    @Bean
    public Step createLottoNumberStep(PlatformTransactionManager transactionManager) {
        return stepBuilderFactory.get("createLottoNumberStep").tasklet(createLottoNumberTasklet).transactionManager(transactionManager).build();
    }

    @Bean
    public Step createLottoStep(PlatformTransactionManager transactionManager) {
        return stepBuilderFactory.get("createLottoStep").tasklet(createLottoTasklet).transactionManager(transactionManager).build();
    }

    // ItemReader 종류 상당히 많다. 상황에 맞는것을 선택해서 사용
//    @Bean
//    public JpaCursorItemReader<LottoEntity> JpaCursorItemReaderBuilder(LocalDate localDate) {
//        return new JpaCursorItemReaderBuilder<LottoEntity>()
//                .name("lottoItemReader")
//                .entityManagerFactory(entityManagerFactory)
//                .queryString("SELECT le FROM LottoEntity le where le.targetDate = :targetDate")
//                .parameterValues(Map.of("targetDate", LocalDate.now()))
//                .build();
//    }
}
