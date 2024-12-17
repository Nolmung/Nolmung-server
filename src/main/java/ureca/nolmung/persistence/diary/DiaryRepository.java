package ureca.nolmung.persistence.diary;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ureca.nolmung.business.diary.dto.response.DeleteDiaryResp;
import ureca.nolmung.jpa.diary.Diary;

@Repository
public interface DiaryRepository extends JpaRepository<Diary, Long> {

    @Query("SELECT d " +
            "FROM Diary d " +
            "JOIN FETCH d.user u " +
            "LEFT JOIN FETCH d.mediaList m " +
            "WHERE d.user.id = :userId " +
            "ORDER BY d.createdAt DESC")
    List<Diary> findDiaryWithMediaByUser(@Param("userId") Long userId);

    @Query("SELECT d FROM Diary d LEFT JOIN FETCH d.mediaList WHERE d.id = :diaryId")
    Diary findWithMediaById(@Param("diaryId") Long diaryId);

    @Query("SELECT COUNT(d) > 0 FROM Diary d WHERE d.user.id = :userId AND DATE(d.createdAt) = :date")
    boolean existsByUserIdAndCreatedAt(Long userId, LocalDate date);
}
