package ureca.nolmung.business.diary.response;

import java.time.LocalDateTime;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import ureca.nolmung.jpa.diary.Diary;
import ureca.nolmung.jpa.media.Media;

@Getter
@NoArgsConstructor
public class PlaceDiaryResponse {

	private Long diaryId;
	private String diaryName;
	private String diaryWriter;
	private String writerUrlImage;
	private String diaryContent;
	private LocalDateTime createdAt;
	private String imageUrl;

	@Builder
	public PlaceDiaryResponse(Long diaryId, String diaryName, String diaryWriter, String writerUrlImage,
		String diaryContent, LocalDateTime createdAt, String imageUrl, double rating) {
		this.diaryId = diaryId;
		this.diaryName = diaryName;
		this.diaryWriter = diaryWriter;
		this.writerUrlImage = writerUrlImage;
		this.diaryContent = diaryContent;
		this.createdAt = createdAt;
		this.imageUrl = imageUrl;
	}

	public PlaceDiaryResponse of(Diary diary, String mediaUrl) {
		return PlaceDiaryResponse.builder()
			.diaryId(diary.getId())
			.diaryName(diary.getTitle())
			.diaryWriter(diary.getUser().getName())
			.writerUrlImage(diary.getUser().getProfileImageUrl())
			.diaryContent(diary.getContent())
			.createdAt(diary.getCreatedAt())
			.imageUrl(mediaUrl)
			.build();
	}

}
