package ureca.nolmung.persistence.dogdiary;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ureca.nolmung.jpa.dogdiary.DogDiary;

import java.util.List;

@Repository
public interface DogDiaryRepository extends JpaRepository<DogDiary, Long> {
    List<DogDiary> findByDiaryId(Long diaryId);
}
