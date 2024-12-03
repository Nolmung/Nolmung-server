package ureca.nolmung.business.diary.dto.request;

import java.util.List;

public record UpdateDiaryReq(
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
