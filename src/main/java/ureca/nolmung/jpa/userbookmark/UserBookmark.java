package ureca.nolmung.jpa.userbookmark;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class UserBookmark {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_bookmark_id")
    private Long id;

    @Column(name = "user_id")
    private Long userId;

    @Column(name = "bookmark_count")
    private int bookmarkCount;
}
