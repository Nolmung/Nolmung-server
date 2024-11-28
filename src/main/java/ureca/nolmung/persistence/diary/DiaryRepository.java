package ureca.nolmung.persistence.diary;

import java.util.List;
import java.util.Map;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ureca.nolmung.jpa.diary.Diary;

@Repository
public interface DiaryRepository extends JpaRepository<Diary, Long> {

    @Query(value = "SELECT d.diary_id AS diaryId, " +
            "u.user_id AS userId, " +
            "COALESCE(u.profile_image_url, '') AS profileImageUrl, " +
            "u.nickname AS nickname, " +
            "d.title AS title, " +
            "d.content AS content, " +
            "d.public_yn AS publicYn, " +
            "DATE_FORMAT(d.created_at, '%Y.%m.%d') AS createdAt, " +
            "COALESCE(sub_m.media_url, '') AS mediaUrl " +
            "FROM diary d " +
            "INNER JOIN user u ON u.user_id = d.user_id " +
            "LEFT JOIN ( " +
            "    SELECT m.diary_id, MIN(m.media_url) AS media_url " +
            "    FROM media m " +
            "    GROUP BY m.diary_id " +
            ") sub_m ON sub_m.diary_id = d.diary_id " +
            "WHERE u.user_id = :userId " +
            "ORDER BY d.created_at DESC",
            nativeQuery = true)
    List<Map<String, Object>> findDiariesWithFirstMediaByUser(@Param("userId") Long userId);
}
