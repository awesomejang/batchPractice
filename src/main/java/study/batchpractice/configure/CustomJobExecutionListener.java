package study.batchpractice.configure;

import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobExecutionListener;

@Slf4j
public class CustomJobExecutionListener implements JobExecutionListener {
    @Override
    public void beforeJob(JobExecution jobExecution) {
        log.info(">>>>> Job Instance START = {}", jobExecution.getJobInstance());
    }

    @Override
    public void afterJob(JobExecution jobExecution) {
        log.info(">>>>> Job Instance END = {}", jobExecution.getJobInstance());
//        jobExecution.setExitStatus(ExitStatus.COMPLETED);
    }
}
