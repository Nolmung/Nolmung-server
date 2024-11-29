package ureca.nolmung.persistence.diary;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ureca.nolmung.jpa.diary.Diary;

@Repository
public interface DiaryRepository extends JpaRepository<Diary, Long> {

    @Query("SELECT d " +
            "FROM Diary d " +
            "JOIN FETCH d.user u " +
            "LEFT JOIN FETCH d.mediaList m " +
            "WHERE u.id = :userId " +
            "AND (m.mediaType = 'IMAGE' OR m IS NULL) " +
            "ORDER BY d.createdAt DESC")
    List<Diary> findDiariesWithFirstMediaByUser(@Param("userId") Long userId);
}
