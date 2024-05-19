package study.batchpractice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import study.batchpractice.entities.LottoEntity;

import java.time.LocalDate;

@Repository
public interface LottoRepository extends JpaRepository<LottoEntity, Long> {

    int countByTargetDate(LocalDate targetDate);
}
