package study.batchpractice.reader;

import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.database.JpaCursorItemReader;
import org.springframework.batch.item.database.builder.JpaCursorItemReaderBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import study.batchpractice.entities.LottoEntity;

import javax.persistence.EntityManagerFactory;
import java.time.LocalDate;
import java.util.Map;

@Slf4j
public class LottoItemReader {
    private final EntityManagerFactory entityManagerFactory;

    public LottoItemReader(EntityManagerFactory entityManagerFactory) {
        this.entityManagerFactory = entityManagerFactory;
    }

    public JpaCursorItemReader<LottoEntity> build(LocalDate targetDate) {
        return new JpaCursorItemReaderBuilder<LottoEntity>()
                .name("lottoItemReader")
                .entityManagerFactory(entityManagerFactory)
                .queryString("SELECT le FROM LottoEntity le where le.targetDate = :targetDate")
                .parameterValues(Map.of("targetDate", targetDate))
                .build();
    }
}
