package ureca.nolmung.jpa.media;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import ureca.nolmung.jpa.config.BaseEntity;
import ureca.nolmung.jpa.diary.Diary;
import ureca.nolmung.jpa.media.Enum.MediaType;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Media extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "media_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "diary_id")
    private Diary diary;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private MediaType mediaType;

    @Column(columnDefinition = "TEXT")
    private String mediaUrl;

    public void addDiary(Diary diary) {
        this.diary = diary;
    }
}
