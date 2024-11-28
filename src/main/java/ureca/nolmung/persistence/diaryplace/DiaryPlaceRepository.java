package ureca.nolmung.persistence.diaryplace;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ureca.nolmung.jpa.diaryplace.DiaryPlace;
import ureca.nolmung.jpa.place.Place;

@Repository
public interface DiaryPlaceRepository extends JpaRepository<DiaryPlace, Long> {

	List<DiaryPlace> findAllByPlaceOrderByCreatedAtDesc(Place place);

}
