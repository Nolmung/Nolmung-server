package ureca.nolmung.business.dog.dto.request;

import java.time.LocalDate;

import ureca.nolmung.jpa.dog.Enum.DogSize;
import ureca.nolmung.jpa.dog.Enum.Gender;

public record DogReq(String dogName, String dogType, LocalDate birth, String profileUrl, Gender gender, DogSize size, boolean neuterYn) {
}



