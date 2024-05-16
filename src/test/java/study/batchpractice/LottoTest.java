package study.batchpractice;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import study.batchpractice.Tasks.LottoCountCheckTasklet;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;

@Transactional
@SpringBootTest

public class LottoTest {

    @Autowired
    private LottoCountCheckTasklet lottoCountCheckTasklet;

    @Test
    public void makeSaturdayDate() {
        LocalDate now = LocalDate.now();
        System.out.println("now = " + now.with(TemporalAdjusters.nextOrSame(DayOfWeek.SATURDAY)));
    }
}
