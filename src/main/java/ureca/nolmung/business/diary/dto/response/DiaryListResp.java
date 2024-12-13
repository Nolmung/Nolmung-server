package ureca.nolmung.business.diary.dto.response;

import ureca.nolmung.jpa.media.Enum.MediaType;

import java.util.List;

public record DiaryListResp(User user,List<Diary> diaries) {
    public record User(
            Long userId,
            String profileImageUrl,
            String nickname
    ) {}

    public record Diary(
            Long diaryId,
            String title,
            String content,
            Boolean publicYn,
            String createdAt,
            List<Media> mediaList
    ) {}

    public record Media(
            Long mediaId,
            MediaType mediaType,
            String mediaUrl
    ) {}
}