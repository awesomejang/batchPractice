package study.batchpractice;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import study.batchpractice.Tasks.LottoCountCheckTasklet;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;
import java.util.HashSet;
import java.util.Random;

@Transactional
//@SpringBootTest
public class LottoTest {

//    @Autowired
//    private LottoCountCheckTasklet lottoCountCheckTasklet;

    @Test
    public void makeSaturdayDate() {
        LocalDate now = LocalDate.now();
        System.out.println("now = " + now.with(TemporalAdjusters.nextOrSame(DayOfWeek.SATURDAY)));
    }

    @Test
    public void createLottoNumbers() {
        HashSet<Integer> numbers = new HashSet<>();
        Random random = new Random();
        while (numbers.size() < 6) {
            // 0 ~ ...
            int number = random.nextInt(45) + 1;
            numbers.add(number);
        }
        Assertions.assertThat(numbers.size()).isEqualTo(6);
        Assertions.assertThat(numbers).allMatch(number -> number >= 1 && number <= 45);
    }
}
