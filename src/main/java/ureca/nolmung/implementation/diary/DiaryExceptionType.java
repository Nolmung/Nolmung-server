package ureca.nolmung.implementation.diary;

import lombok.AllArgsConstructor;
import ureca.nolmung.exception.ExceptionType;
import ureca.nolmung.exception.Status;

@AllArgsConstructor
public enum DiaryExceptionType implements ExceptionType {

    DIARY_NOT_FOUND_EXCEPTION(Status.NOT_FOUND, "일기를 찾을 수 없습니다."),
    DIARY_UNAUTHORIZED_EXCEPTION(Status.UNAUTHORIZED, "접근 권한이 없습니다."),
    DIARY_UNAUTHORIZED_EXCEPTION(Status.UNAUTHORIZED, "수정 권한이 없습니다."),
    DIARY_TITLE_CONTAINS_BAN_WORD(Status.BAD_REQUEST, "제목에 금칙어가 있습니다."),
    DIARY_CONTENT_CONTAINS_BAN_WORD(Status.BAD_REQUEST, "내용에 금칙어가 있습니다."),
    ;

    private final Status status;
    private final String message;

    @Override
    public Status status() {
        return status;
    }

    @Override
    public String message() {
        return message;
    }
}
