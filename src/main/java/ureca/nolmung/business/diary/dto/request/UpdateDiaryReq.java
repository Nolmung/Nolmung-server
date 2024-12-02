package ureca.nolmung.business.diary.dto.request;

import java.util.List;

public record UpdateDiaryReq(
    Long userId,
    String title,
    String content,
    List<Long> dogs,
    Boolean publicYn,
    List<MediaDto> medias
) {
    public record MediaDto(
        String mediaType,
        String mediaUrl
    ) {}
}
