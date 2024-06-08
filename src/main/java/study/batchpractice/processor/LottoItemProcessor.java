package study.batchpractice.processor;

import study.batchpractice.entities.LottoEntity;
import study.batchpractice.entities.TotalLottoEntity;

import javax.batch.api.chunk.ItemProcessor;

public class LottoItemProcessor implements ItemProcessor<LottoEntity, TotalLottoEntity> {

    @Override
    public TotalLottoEntity processItem(LottoEntity item) {
        return TotalLottoEntity.builder()
                .targetDate(item.getTargetDate())
                .totalAmount(item.getTotalAmount())
                .totalCount(item.getTotalCount())
                .build();
    }

    @Override
    public Object processItem(Object o) throws Exception {
        return null;
    }
}
