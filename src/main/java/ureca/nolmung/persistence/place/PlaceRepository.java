package ureca.nolmung.persistence.place;

import java.util.List;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import ureca.nolmung.jpa.place.Place;

@Repository
public interface PlaceRepository extends JpaRepository<Place, Long>, PlaceRepositoryCustom {

	@Query("select p from Place p where lower(p.name) like lower(concat('%', :keyword, '%'))")
	List<Place> searchByKeyword(String keyword);

	@Query("SELECT p FROM Place p ORDER BY p.bookmarkCount DESC")
	List<Place> findTopNPlaces(PageRequest pageRequest);

	@Query(value = "SELECT * FROM place p " +
			"WHERE p.address LIKE CONCAT('%', :addressProvince, '%') " +
			"ORDER BY RAND() " +
			"LIMIT 5", nativeQuery = true)
	List<Place> findByUserAddress(@Param("addressProvince")String AddressProvince);

}
