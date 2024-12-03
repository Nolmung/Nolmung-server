package ureca.nolmung.config.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ResponseDto<T> {
	private ResponseStatus status;
	private String message;
	private T data;
}
