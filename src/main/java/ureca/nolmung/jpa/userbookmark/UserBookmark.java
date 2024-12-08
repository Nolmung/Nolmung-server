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

    @Column(nullable = false, name = "user_id")
    private Long userId;

    @Column(nullable = false, name = "bookmark_count")
    private int bookmarkCount;

    public static UserBookmark createUserBookmark(Long userId, int userBookmarkCount) {
        UserBookmark userBookmark = new UserBookmark();
        userBookmark.userId = userId;
        userBookmark.bookmarkCount = userBookmarkCount;
        return userBookmark;
    }

    public void updateBookmarkCount(int userBookmarkCount) {
        this.bookmarkCount = userBookmarkCount;
    }
}
