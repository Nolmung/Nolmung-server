package ureca.nolmung.business.dog.dto.response;

import java.time.LocalDate;

public record DogResp(Long dogId, String dogName, String dogType, LocalDate birth, String profileUrl, String gender, String size, boolean neuterYn) {
}

