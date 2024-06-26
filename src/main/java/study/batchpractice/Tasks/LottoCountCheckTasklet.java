package study.batchpractice.Tasks;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.stereotype.Component;
import study.batchpractice.repository.LottoRepository;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;

@Slf4j
@RequiredArgsConstructor
@Component
public class LottoCountCheckTasklet implements Tasklet {

    private final LottoRepository lottoRepository;

    @Override
    public RepeatStatus execute(StepContribution stepContribution, ChunkContext chunkContext) {
        log.info(">>>>> This is LottoCountCheckTasklet");
        int count = lottoRepository.countByTargetDate(LocalDate.now().with(TemporalAdjusters.nextOrSame(DayOfWeek.SATURDAY)));
        log.info(">>>>> Lotto Count = {}", count);
        if(count >= 50) {
            log.info(">>>>> Lotto Count is over 10");
            stepContribution.setExitStatus(new ExitStatus("COUNT_OVER_10"));
            return RepeatStatus.FINISHED;
        }
        return RepeatStatus.FINISHED;
    }
}
