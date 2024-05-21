package study.batchpractice.Tasks;

import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;

import java.util.HashSet;
import java.util.Random;
import java.util.stream.Collectors;

// 한게임에 해당하는 로또 번호 6개의 중복되지 않은 숫자를 생성한다. (1~45)
public class CreateLottoNumber implements Tasklet {

    @Override
    public RepeatStatus execute(StepContribution stepContribution, ChunkContext chunkContext) throws Exception {

        return null;
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
