package study.batchpractice.writer;

import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.ItemWriter;
import study.batchpractice.entities.TotalLottoEntity;
import study.batchpractice.repository.TotalLottoRepository;

import java.util.List;

@Slf4j
public class TotalLottoItemWriter implements ItemWriter<TotalLottoEntity> {

    private final TotalLottoRepository totalLottoRepository;

    public TotalLottoItemWriter(TotalLottoRepository totalLottoRepository) {
        this.totalLottoRepository = totalLottoRepository;
    }

    @Override
    public void write(List<? extends TotalLottoEntity> list) throws Exception {
        log.info(">>>>> This is TotalLottoItemWriter");
        totalLottoRepository.saveAll(list);
    }
}
