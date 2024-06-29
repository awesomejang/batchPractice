package study.batchpractice.processor;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.ItemProcessor;
import study.batchpractice.entities.LottoEntity;

import java.time.LocalDate;

@Slf4j
@RequiredArgsConstructor
public class LottoItemProcessor implements ItemProcessor<LottoEntity, String> {

    private final LocalDate targetDate;

    @Override
    public String process(LottoEntity lottoEntity) throws Exception {
        log.info(">>>>> This is LottoItemProcessor");
        log.info(">>>>> lottoNumber = {}", lottoEntity.getLottoNumbers());
        lottoEntity.setUseYn("Y");
        return lottoEntity.getLottoNumbers();
    }
}
