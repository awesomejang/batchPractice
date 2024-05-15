package study.batchpractice.configure.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import study.batchpractice.configure.entities.LottoEntity;

public interface LottoRepository extends JpaRepository<LottoEntity, Long> {
}
