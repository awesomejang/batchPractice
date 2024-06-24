package study.batchpractice.processor;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;
import study.batchpractice.entities.LottoEntity;
import study.batchpractice.entities.TotalLottoEntity;

import java.time.LocalDate;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
public class LottoItemProcessor implements ItemProcessor<LottoEntity, String> {

    private final LocalDate targetDate;

//    @Override
//    public LottoEntity process(LottoEntity lottoEntity) throws Exception {
//        log.info(">>>>> This is LottoItemProcessor");
//        log.info(">>>>> lottoNumber = {}", lottoEntity.getLottoNumbers());
//        ObjectMapper objectMapper = new ObjectMapper();
//        ArrayNode arrayNode = objectMapper.createArrayNode();
////        for (int i = 0; i < lottoEntities.size(); i++) {
////            ObjectNode objectNode = objectMapper.createObjectNode();
////            objectNode.put(String.valueOf(i + 1), lottoEntities.get(i).getLottoNumbers());
////            arrayNode.add(objectNode);
////        }
//        String lottoNumberJson = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(arrayNode);
//
////        return new TotalLottoEntity(lottoNumberJson, targetDate);
//        return lottoEntity;
//    }


    @Override
    public String process(LottoEntity lottoEntity) throws Exception {
        log.info(">>>>> This is LottoItemProcessor");
        log.info(">>>>> lottoNumber = {}", lottoEntity.getLottoNumbers());
        return lottoEntity.getLottoNumbers();
    }
}
