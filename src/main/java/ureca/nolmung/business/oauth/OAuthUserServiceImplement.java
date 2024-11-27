package ureca.nolmung.business.oauth;

import lombok.RequiredArgsConstructor;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import ureca.nolmung.business.oauth.dto.CustomOauth2User;
import ureca.nolmung.jpa.user.Enum.UserRole;
import ureca.nolmung.jpa.user.User;
import ureca.nolmung.persistence.user.UserRepository;

import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class OAuthUserServiceImplement extends DefaultOAuth2UserService {

    private final UserRepository userRepository;
    @Override
    public OAuth2User loadUser(OAuth2UserRequest request) throws OAuth2AuthenticationException
    {
        OAuth2User oAuth2User = super.loadUser(request);
        String oauthClientName = request.getClientRegistration().getClientName();

        Long id = null;
        String email = null;
        UserRole role = null;

        if(oauthClientName.equals("Kakao"))
        {
            User oauthClient = null;
            Map<String,Object> account_map = (Map<String,Object>)oAuth2User.getAttributes().get("kakao_account");
            email = (String)account_map.get("email");

            // 회원가입 되어있는지 확인
            Optional<User> userOptional = userRepository.findByEmail(email);

            if (userOptional.isPresent()) {
                //System.out.println("로그인 필요!");

                oauthClient = userOptional.get(); // user 객체를 가져와야 합니다.
                //role = UserRole.USER;
                role = oauthClient.getRole();
                id = oauthClient.getId(); // 사용자 ID 할당

            }
            else {
                //System.out.println("회원가입 필요!");

                Map<String,Object> properties_map = (Map<String,Object>)oAuth2User.getAttributes().get("properties");
                String nickname = (String) properties_map.get("nickname");
                String profile_image = (String)properties_map.get("profile_image");

                oauthClient = new User(nickname, profile_image, email);
                oauthClient = userRepository.save(oauthClient);
                role = UserRole.GUEST;
                id = oauthClient.getId();
            }
        }

        return new CustomOauth2User(id, email, role);
    }
}
