package ureca.nolmung.jpa.place.Enum;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Category {
    ALL("공통"),
    MUSEUM("박물관"),
    TRAVEL("여행지"),
    ART_GALLERY("미술관"),
    ACCOMMODATION("숙소"),
    PLAYGROUND("놀이터"),
    PARK("공원"),
    RESTAURANT("식당"),
    CAFE("카페");

    private final String text;
}
