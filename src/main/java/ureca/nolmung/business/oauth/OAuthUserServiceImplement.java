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
        // OAuth2 인증 서버로부터 사용자 정보를 로드
        OAuth2User oAuth2User = super.loadUser(request);
        String oauthClientName = request.getClientRegistration().getClientName();

        User oauthClient = null;

        // 1. 카카오 OAuth 클라이언트인 경우
        if(oauthClientName.equals("Kakao"))
        {
            Map<String,Object> account_map = (Map<String,Object>)oAuth2User.getAttributes().get("kakao_account");
            String email = (String)account_map.get("email");

            // 이미 가입된 사용자 여부를 확인
            Optional<User> user = userManager.findByEmailAndProvider(email, Provider.KAKAO);

            // 기존에 가입된 사용자가 있다면 해당 사용자 정보를 사용
            if(user.isPresent())
            {
                oauthClient = user.get();
            }
            else
            {
                // 최초 로그인한 사용자라면, 카카오에서 제공하는 닉네임과 프로필 이미지를 가져와서 사용자 등록
                Map<String,Object> properties_map = (Map<String,Object>)oAuth2User.getAttributes().get("properties");
                String name = (String) properties_map.get("nickname");
                String profileImage = (String)properties_map.get("profile_image");

                oauthClient = userManager.registerUserWithSocialLogin(name,profileImage,email,Provider.KAKAO);
            }
        }

        // 2. 예외처리
        if(oauthClient == null)
        {
            throw new UserException(UserExceptionType.USER_NOT_FOUND_EXCEPTION);
        }

        // 3. CustomUserDetails 객체를 반환
        return new CustomUserDetails(oauthClient);
    }

    private OAuth2User getOAuth2User(String code)
    {
        // 인증 코드로 카카오 사용자 정보를 가져오는 메소드

        // 1. ClientRegistration 가져오기
        ClientRegistration clientRegistration = clientRegistrationRepository.findByRegistrationId("kakao");

        // 2. 카카오 클라이언트 등록 정보가 없는 경우 예외 처리
        if (clientRegistration == null) {
            throw new OauthException(OauthExceptionType.CLIENT_REGISTRATION_NOT_FOUND);
        }

        // 3. 액세스 토큰 얻기 (OAuth2 인증 서버에서 교환)
        OAuth2AccessToken accessToken = getAccessToken(clientRegistration, code);

        // 4. OAuth2UserRequest 생성
        OAuth2UserRequest userRequest = new OAuth2UserRequest(clientRegistration, accessToken);

        return loadUser(userRequest);
    }

    private OAuth2AccessToken getAccessToken(ClientRegistration clientRegistration, String code) {
        // 인증코드 사용해서 액세스 토큰 받아오기

        // 1. OAuth2 토큰 교환 요청을 수행하는 HTTP 클라이언트
        RestTemplate restTemplate = new RestTemplate();

        // 2. 요청 파라미터 설정
        MultiValueMap<String, String> tokenRequest = new LinkedMultiValueMap<>();
        tokenRequest.add("grant_type", "authorization_code");
        tokenRequest.add("client_id", clientRegistration.getClientId());
        tokenRequest.add("client_secret", clientRegistration.getClientSecret());
        tokenRequest.add("redirect_uri", clientRegistration.getRedirectUri());
        tokenRequest.add("code", code);

        // 3. 요청 헤더 설정
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        // 4. 요청 엔티티 생성
        HttpEntity<MultiValueMap<String, String>> requestEntity = new HttpEntity<>(tokenRequest, headers);

        // 5. 액세스 토큰 엔드포인트 호출
        ResponseEntity<Map> response = restTemplate.exchange(
                clientRegistration.getProviderDetails().getTokenUri(),
                HttpMethod.POST,
                requestEntity,
                Map.class
        );

        // 6. 응답에서 액세스 토큰 추출
        Map responseBody = response.getBody();
        if (responseBody == null || !responseBody.containsKey("access_token")) {
            throw new OauthException(OauthExceptionType.ACCESS_TOKEN_RETRIEVAL_FAILED);
        }

        // 7. 토큰 유효 시간 설정
        Instant issuedAt = Instant.now();
        Instant expiresAt = issuedAt.plusSeconds((Integer) responseBody.get("expires_in"));

        // 8. 액세스 토큰 객체 반환
        return new OAuth2AccessToken(
                OAuth2AccessToken.TokenType.BEARER,
                (String) responseBody.get("access_token"),
                issuedAt,
                expiresAt
        );
    }

    public Long kakaoOauthLogin(String code)
    {
        // 인증코드를 사용해서 카카오로부터 사용자 정보 받아오기
        CustomUserDetails userDetails = (CustomUserDetails)getOAuth2User(code);
        return userDetails.getUser().getId();
    }
}