package ureca.nolmung.business.dog.dto.response;

import java.time.LocalDate;

import ureca.nolmung.jpa.dog.Enum.DogSize;
import ureca.nolmung.jpa.dog.Enum.Gender;

public record DogResp(Long dogId, String dogName, String dogType, LocalDate birth, String profileUrl, Gender gender, DogSize size, boolean neuterYn) {
}

