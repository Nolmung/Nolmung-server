package ureca.nolmung.implementation.media;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ureca.nolmung.jpa.media.Media;
import ureca.nolmung.persistence.media.MediaRepository;

import java.util.List;

@Component
@RequiredArgsConstructor
public class MediaManager {

    private final MediaRepository mediaRepository;

    public List<Media> getMediaList(Long diaryId) {
        return mediaRepository.findByDiaryId(diaryId);
    }
}
