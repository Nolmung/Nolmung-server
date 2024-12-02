package ureca.nolmung.business.oauth;

import java.util.Map;
import java.util.Optional;

import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import ureca.nolmung.business.user.dto.response.CustomUserDetails;
import ureca.nolmung.implementation.user.UserManager;
import ureca.nolmung.jpa.user.Enum.Provider;
import ureca.nolmung.jpa.user.User;

@Service
@RequiredArgsConstructor
public class OAuthUserServiceImplement extends DefaultOAuth2UserService {

    private final UserManager userManager;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest request) throws OAuth2AuthenticationException
    {
        OAuth2User oAuth2User = super.loadUser(request);
        String oauthClientName = request.getClientRegistration().getClientName();

        User oauthClient = null;

        if(oauthClientName.equals("Kakao"))
        {
            Map<String,Object> account_map = (Map<String,Object>)oAuth2User.getAttributes().get("kakao_account");
            String email = (String)account_map.get("email");

            Optional<User> user = userManager.findByEmailAndProvider(email, Provider.KAKAO);

            if(user.isPresent())
            {
                oauthClient = user.get();
            }
            else
            {
                Map<String,Object> properties_map = (Map<String,Object>)oAuth2User.getAttributes().get("properties");
                String name = (String) properties_map.get("nickname");
                String profileImage = (String)properties_map.get("profile_image");

                oauthClient = userManager.registerUserWithSocialLogin(name,profileImage,email,Provider.KAKAO);
            }
        }

        return new CustomUserDetails(oauthClient);
    }
}
