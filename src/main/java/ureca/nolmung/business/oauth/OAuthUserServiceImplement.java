package ureca.nolmung.business.oauth;

import java.time.Instant;
import java.util.Map;
import java.util.Optional;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AccessToken;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import lombok.RequiredArgsConstructor;
import ureca.nolmung.business.user.dto.response.CustomUserDetails;
import ureca.nolmung.config.jwt.JWTUtil;
import ureca.nolmung.implementation.oauth.OauthException;
import ureca.nolmung.implementation.oauth.OauthExceptionType;
import ureca.nolmung.implementation.user.UserException;
import ureca.nolmung.implementation.user.UserExceptionType;
import ureca.nolmung.implementation.user.UserManager;
import ureca.nolmung.jpa.user.Enum.Provider;
import ureca.nolmung.jpa.user.User;

@Service
@RequiredArgsConstructor
public class OAuthUserServiceImplement extends DefaultOAuth2UserService {

    private final UserManager userManager;
    private final ClientRegistrationRepository clientRegistrationRepository;
    private final JWTUtil jwtUtil;

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

        if(oauthClient == null)
        {
            throw new UserException(UserExceptionType.USER_NOT_FOUND_EXCEPTION);
        }

        return new CustomUserDetails(oauthClient);
    }

    private OAuth2User getOAuth2User(String code)
    {
        // 1. ClientRegistration 가져오기
        ClientRegistration clientRegistration = clientRegistrationRepository.findByRegistrationId("kakao");

        if (clientRegistration == null) {
            throw new OauthException(OauthExceptionType.CLIENT_REGISTRATION_NOT_FOUND);
        }

        // 2. 액세스 토큰 얻기 (OAuth2 인증 서버에서 교환)
        OAuth2AccessToken accessToken = getAccessToken(clientRegistration, code);

        // 3. OAuth2UserRequest 생성
        OAuth2UserRequest userRequest = new OAuth2UserRequest(clientRegistration, accessToken);

        return loadUser(userRequest);
    }

    private OAuth2AccessToken getAccessToken(ClientRegistration clientRegistration, String code) {
        // OAuth2 토큰 교환 요청을 수행하는 HTTP 클라이언트
        RestTemplate restTemplate = new RestTemplate();

        // 요청 파라미터 설정
        MultiValueMap<String, String> tokenRequest = new LinkedMultiValueMap<>();
        tokenRequest.add("grant_type", "authorization_code");
        tokenRequest.add("client_id", clientRegistration.getClientId());
        tokenRequest.add("client_secret", clientRegistration.getClientSecret());
        tokenRequest.add("redirect_uri", clientRegistration.getRedirectUri());
        tokenRequest.add("code", code);

        // 요청 헤더 설정
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        // 요청 엔티티 생성
        HttpEntity<MultiValueMap<String, String>> requestEntity = new HttpEntity<>(tokenRequest, headers);

        // 액세스 토큰 엔드포인트 호출
        ResponseEntity<Map> response = restTemplate.exchange(
                clientRegistration.getProviderDetails().getTokenUri(),
                HttpMethod.POST,
                requestEntity,
                Map.class
        );

        // 응답에서 액세스 토큰 추출
        Map responseBody = response.getBody();
        if (responseBody == null || !responseBody.containsKey("access_token")) {
            throw new OauthException(OauthExceptionType.ACCESS_TOKEN_RETRIEVAL_FAILED);
        }

        Instant issuedAt = Instant.now();
        Instant expiresAt = issuedAt.plusSeconds((Integer) responseBody.get("expires_in"));

        return new OAuth2AccessToken(
                OAuth2AccessToken.TokenType.BEARER,
                (String) responseBody.get("access_token"),
                issuedAt,
                expiresAt
        );
    }

    public Long kakaoOauthLogin(String code)
    {
        CustomUserDetails userDetails = (CustomUserDetails)getOAuth2User(code);
        return userDetails.getUser().getId();
    }
}