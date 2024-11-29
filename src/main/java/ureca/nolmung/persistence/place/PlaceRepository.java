package ureca.nolmung.persistence.place;

import java.util.List;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import ureca.nolmung.jpa.dog.Enum.DogSize;
import ureca.nolmung.jpa.place.Place;

@Repository
public interface PlaceRepository extends JpaRepository<Place, Long> {

	@Query("select p from Place p where lower(p.name) like lower(concat('%', :keyword, '%'))")
	List<Place> searchByKeyword(String keyword);

	@Query("SELECT p FROM Place p ORDER BY p.bookmarkCount DESC")
	List<Place> findTopNPlaces(PageRequest pageRequest);

	@Query("SELECT p FROM Place p WHERE p.acceptSize LIKE CONCAT('%', :size, '%')")
	List<Place> findAllByDogSize(@Param("size") String size);


}
