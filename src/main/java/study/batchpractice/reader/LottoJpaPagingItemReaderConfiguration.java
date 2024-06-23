package study.batchpractice.reader;

import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.database.JpaPagingItemReader;
import org.springframework.batch.item.database.builder.JpaPagingItemReaderBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import study.batchpractice.entities.LottoEntity;

import javax.persistence.EntityManagerFactory;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;
import java.util.Map;

@Slf4j
@Configuration
public class LottoJpaPagingItemReaderConfiguration {
    private final EntityManagerFactory entityManagerFactory;

    public LottoJpaPagingItemReaderConfiguration(EntityManagerFactory entityManagerFactory) {
        this.entityManagerFactory = entityManagerFactory;
    }

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
}
