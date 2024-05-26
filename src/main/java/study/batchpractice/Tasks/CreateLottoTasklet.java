package study.batchpractice.Tasks;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.item.ExecutionContext;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.stereotype.Component;
import study.batchpractice.entities.LottoEntity;
import study.batchpractice.repository.LottoRepository;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;

@Slf4j
@Component
@Getter
public class CreateLottoTasklet implements Tasklet {

    private final LottoRepository lottoRepository;

    CreateLottoTasklet(LottoRepository lottoRepository) {
        this.lottoRepository = lottoRepository;
    }

    @Override
    public RepeatStatus execute(StepContribution stepContribution, ChunkContext chunkContext) throws Exception {
        log.info(">>>>> This is createLotto");
        ExecutionContext executionContext = chunkContext.getStepContext().getStepExecution().getJobExecution().getExecutionContext();

        String lottoNumbers = executionContext.getString("lottoNumbers");
        log.info(">>>>> RECEIVED lottoNumbers = {}", lottoNumbers);
        LottoEntity lottoEntity = new LottoEntity(lottoNumbers, createTargetDate());
        lottoRepository.save(lottoEntity);

        return RepeatStatus.FINISHED;
    }

    private LocalDate createTargetDate() {
        return LocalDate.now().with(TemporalAdjusters.nextOrSame(DayOfWeek.SATURDAY));
    }
}
