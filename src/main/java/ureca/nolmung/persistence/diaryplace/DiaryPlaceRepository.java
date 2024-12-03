package ureca.nolmung.persistence.diaryplace;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import ureca.nolmung.jpa.diaryplace.DiaryPlace;
import ureca.nolmung.jpa.place.Place;

@Repository
public interface DiaryPlaceRepository extends JpaRepository<DiaryPlace, Long> {

	List<DiaryPlace> findAllByPlaceOrderByCreatedAtDesc(Place place);

	@Query("SELECT dp.place.id FROM DiaryPlace dp WHERE dp.diary.id = :diaryId")
	List<Long> findPlaceIdsByDiaryId(Long diaryId);
}
