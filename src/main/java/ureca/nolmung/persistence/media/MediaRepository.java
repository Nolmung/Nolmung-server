package ureca.nolmung.persistence.media;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ureca.nolmung.jpa.diary.Diary;
import ureca.nolmung.jpa.media.Media;

import java.util.List;

@Repository
public interface MediaRepository extends JpaRepository<Media, Long> {

	Media findFirstByDiary(Diary diary);

	List<Media> findByDiaryId(Long diaryId);

	void deleteByDiaryId(Long id);
}
