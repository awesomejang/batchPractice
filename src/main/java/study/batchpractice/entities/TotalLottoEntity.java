package study.batchpractice.entities;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "total_lottos")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class TotalLottoEntity extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "total_lotto_numbers", columnDefinition = "VARCHAR(2000)")
    private String totalLottoNumbers;

    @Column(name = "target_date", columnDefinition = "DATE")
    private LocalDate targetDate;

    public TotalLottoEntity(String totalLottoNumbers, LocalDate targetDate) {
        this.totalLottoNumbers = totalLottoNumbers;
        this.targetDate = targetDate;
    }
}
