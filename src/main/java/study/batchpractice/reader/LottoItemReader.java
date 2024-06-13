package study.batchpractice.reader;

import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.NonTransientResourceException;
import org.springframework.batch.item.ParseException;
import org.springframework.batch.item.UnexpectedInputException;
import study.batchpractice.entities.LottoEntity;
import study.batchpractice.repository.LottoRepository;

import java.time.LocalDate;
import java.util.List;

@Slf4j
//@RequiredArgsConstructor
public class LottoItemReader implements ItemReader<List<LottoEntity>> {

    private final LottoRepository lottoRepository;
    private final LocalDate localDate;

    public LottoItemReader(LottoRepository lottoRepository, LocalDate localDate) {
        this.lottoRepository = lottoRepository;
        this.localDate = localDate;
    }

    @Override
    public List<LottoEntity> read() throws Exception, UnexpectedInputException, ParseException, NonTransientResourceException {
        log.info(">>>>> This is LottoItemReader");
        return lottoRepository.findByTargetDate(this.localDate);
    }


//    private final EntityManagerFactory entityManagerFactory;
//
//    public LottoItemReader(EntityManagerFactory entityManagerFactory) {
//        this.entityManagerFactory = entityManagerFactory;
//    }
//
//    public JpaCursorItemReader<LottoEntity> build(LocalDate targetDate) {
//        return new JpaCursorItemReaderBuilder<LottoEntity>()
//                .name("lottoItemReader")
//                .entityManagerFactory(entityManagerFactory)
//                .queryString("SELECT le FROM LottoEntity le where le.targetDate = :targetDate")
//                .parameterValues(Map.of("targetDate", targetDate))
//                .build();
//    }
}
