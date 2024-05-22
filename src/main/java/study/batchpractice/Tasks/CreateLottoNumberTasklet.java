package study.batchpractice.Tasks;

import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.item.ExecutionContext;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Random;
import java.util.stream.Collectors;

// 한게임에 해당하는 로또 번호 6개의 중복되지 않은 숫자를 생성한다. (1~45)
@Slf4j
@Component
public class CreateLottoNumberTasklet implements Tasklet {

    @Override
    public RepeatStatus execute(StepContribution stepContribution, ChunkContext chunkContext) throws Exception {
        log.info(">>>>> This is CreateLottoNumber");
        String lottoNumbers = createLottoNumbers();
        log.info(">>>>> CREATED lottoNumbers = {}", lottoNumbers);
        StepExecution stepExecution = chunkContext.getStepContext().getStepExecution();
        ExecutionContext executionContext = stepExecution.getJobExecution().getExecutionContext();
        executionContext.put("lottoNumbers", lottoNumbers);
        return RepeatStatus.FINISHED;
    }

    public String createLottoNumbers() {
        HashSet<Integer> numbers = new HashSet<>();
        Random random = new Random();
        while (numbers.size() < 6) {
            // 0 ~ ...
            int number = random.nextInt(45) + 1;
            numbers.add(number);
        }
        return numbers.stream().map(String::valueOf).collect(Collectors.joining(","));
    }
}

