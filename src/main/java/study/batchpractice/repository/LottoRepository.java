package study.batchpractice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import study.batchpractice.entities.LottoEntity;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface LottoRepository extends CrudRepository<LottoEntity, Long> {

    int countByTargetDate(LocalDate targetDate);

    List<LottoEntity> findByTargetDate(LocalDate targetDate);
}
