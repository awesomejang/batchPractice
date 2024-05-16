package study.batchpractice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import study.batchpractice.entities.LottoEntity;

import java.time.LocalDate;

public interface LottoRepository extends JpaRepository<LottoEntity, Long> {

    public int countByTargetDate(LocalDate targetDate);
}
