package ureca.nolmung.persistence.diary;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ureca.nolmung.jpa.diary.Diary;

@Repository
public interface DiaryRepository extends JpaRepository<Diary, Long> {

}
