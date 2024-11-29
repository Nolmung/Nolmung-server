package ureca.nolmung.implementation.dog.dtomapper;

import org.springframework.stereotype.Component;
import ureca.nolmung.business.dog.dto.response.DogResp;
import ureca.nolmung.jpa.dog.Dog;

@Component
public class DogDtoMapper {

    public DogResp toDogResp(Dog dog) {
        return new DogResp(dog.getId(), dog.getName(), dog.getType(), dog.getBirth(),
                dog.getProfileImageUrl(), dog.getGender().toString(), dog.getSize().toString(), dog.isNeuteredYn());
    }
}
