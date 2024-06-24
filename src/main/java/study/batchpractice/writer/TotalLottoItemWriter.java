package study.batchpractice.writer;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.ItemWriter;
import study.batchpractice.entities.TotalLottoEntity;
import study.batchpractice.repository.TotalLottoRepository;

import java.util.List;

@Slf4j
public class TotalLottoItemWriter implements ItemWriter<String> {

    private final TotalLottoRepository totalLottoRepository;

    public TotalLottoItemWriter(TotalLottoRepository totalLottoRepository) {
        this.totalLottoRepository = totalLottoRepository;
    }

    @Override
    public void write(List<? extends String> numbers) throws Exception {
        log.info(">>>>> This is TotalLottoItemWriter");
        log.info(">>>>> numbers = {}", numbers);

        totalLottoRepository.save(new TotalLottoEntity(new ObjectMapper().writeValueAsString(numbers), null));
    }
}
