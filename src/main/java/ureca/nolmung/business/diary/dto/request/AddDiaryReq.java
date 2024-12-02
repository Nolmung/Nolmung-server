package ureca.nolmung.business.diary.dto.request;

import java.util.List;

public record AddDiaryReq(
        Long userId,
        String title,
        String content,
        List<Long> places,
        List<MediaDto> medias,
        List<Long> dogs,
        Boolean publicYn
    ) {
    public record MediaDto(
            String mediaType,
            String mediaUrl
    ) {

    }
}
