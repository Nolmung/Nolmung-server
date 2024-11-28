package ureca.nolmung.business.dog.dto.request;

import java.time.LocalDate;

public record AddDogReq(String dogName, String dogType, LocalDate birth, String profileUrl, String gender, String size, boolean neuterYn) {
}



