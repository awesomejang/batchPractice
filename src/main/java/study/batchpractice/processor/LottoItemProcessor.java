package study.batchpractice.processor;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.ItemProcessor;
import study.batchpractice.entities.LottoEntity;
import study.batchpractice.entities.TotalLottoEntity;

import java.util.List;

@Slf4j
public class LottoItemProcessor implements ItemProcessor<List<LottoEntity>, TotalLottoEntity> {

    @Override
    public TotalLottoEntity process(List<LottoEntity> lottoEntities) throws Exception {
        log.info(">>>>> This is LottoItemProcessor");
        log.info(">>>>> lottoEntitiesSize = {}", lottoEntities.size());
        ObjectMapper objectMapper = new ObjectMapper();
        ArrayNode arrayNode = objectMapper.createArrayNode();
        for (int i = 0; i < lottoEntities.size(); i++) {
            ObjectNode objectNode = objectMapper.createObjectNode();
            objectNode.put(String.valueOf(i + 1), lottoEntities.get(i).getLottoNumbers());
            arrayNode.add(objectNode);
        }
        String lottoNumberJson = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(arrayNode);

        return null;
    }
}