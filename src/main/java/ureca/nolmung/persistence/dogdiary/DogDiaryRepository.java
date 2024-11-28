package ureca.nolmung.persistence.dogdiary;

import org.springframework.data.jpa.repository.JpaRepository;
import ureca.nolmung.jpa.dogdiary.DogDiary;

public interface DogDiaryRepository extends JpaRepository<DogDiary, Long> {
}
