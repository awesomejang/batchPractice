package study.batchpractice.entities;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;

@Getter
@Setter
@Entity
@Table(name = "lottos")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class LottoEntity extends BaseTimeEntity {

    public LottoEntity(String lottoNumbers, LocalDate targetDate) {
        this.lottoNumbers = lottoNumbers;
        this.targetDate = targetDate;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "lotto_numbers", columnDefinition = "VARCHAR(400)")
    private String lottoNumbers;

    @Column(name = "target_date", columnDefinition = "DATE")
    private LocalDate targetDate;
}
