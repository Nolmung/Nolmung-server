package ureca.nolmung.business.diary.dto.response;

import java.time.LocalDateTime;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import ureca.nolmung.jpa.diary.Diary;

@Getter
@NoArgsConstructor
public class PlaceDiaryResp {

	private Long diaryId;
	private String diaryName;
	private String diaryWriter;
	private String writerUrlImage;
	private String diaryContent;
	private LocalDateTime createdAt;
	private String imageUrl;

	@Builder
	public PlaceDiaryResp(Long diaryId, String diaryName, String diaryWriter, String writerUrlImage,
		String diaryContent, LocalDateTime createdAt, String imageUrl, double rating) {
		this.diaryId = diaryId;
		this.diaryName = diaryName;
		this.diaryWriter = diaryWriter;
		this.writerUrlImage = writerUrlImage;
		this.diaryContent = diaryContent;
		this.createdAt = createdAt;
		this.imageUrl = imageUrl;
	}

	public PlaceDiaryResp of(Diary diary, String mediaUrl) {
		return PlaceDiaryResp.builder()
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
