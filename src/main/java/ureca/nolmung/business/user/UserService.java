package ureca.nolmung.business.user;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import ureca.nolmung.business.user.dto.request.SignUpReq;
import ureca.nolmung.config.jwt.JWTUtil;
import ureca.nolmung.exception.jwt.JwtException;
import ureca.nolmung.exception.jwt.JwtExceptionType;
import ureca.nolmung.implementation.user.UserException;
import ureca.nolmung.implementation.user.UserExceptionType;
import ureca.nolmung.jpa.user.User;
import ureca.nolmung.persistence.user.UserRepository;

@Service
@RequiredArgsConstructor
public class UserService {

	private final UserRepository userRepository;
	private final JWTUtil jwtUtil;

	@Transactional
	public String completeSignUp(Long userId, SignUpReq req) {

		// 1. 유저 아이디에 해당하는 유저 정보 찾기
		User user = userRepository.findById(userId)
			.orElseThrow(() -> new UserException(UserExceptionType.USER_NOT_FOUND_EXCEPTION)); // 예외 처리

		// 2. 입력받은 회원정보(나이, 성별, 거주지 등) 유저 정보 업데이트
		user.setSignUpReq(req);

		String jwtToken = jwtUtil.createJwt(user.getEmail(), user.getId(), user.getRole()); // 예외 처리

		if (jwtToken == null) {
			throw new JwtException(JwtExceptionType.JWT_TOKEN_CREATION_FAILED_EXCEPTION);
		}

		// 3. 유저 정보로 JWT 토큰 생성 후 반환
		System.out.println("회원가입 후, 토큰 생성 : " + jwtToken);

		return jwtToken;
	}

	public Long login(String token) {

		Long loginUserId = null;

		if(token == null)
		{
			throw new JwtException(JwtExceptionType.JWT_TOKEN_NOT_FOUND_EXCEPTION);
		}
		else
		{
			loginUserId = jwtUtil.getUserId(token);

			if(loginUserId == null)
			{
				throw new JwtException(JwtExceptionType.JWT_USER_ID_NOT_FOUND_EXCEPTION);
			}

			if(!userRepository.existsById(loginUserId))
			{
				throw new UserException(UserExceptionType.USER_NOT_FOUND_EXCEPTION);
			}
		}

		return loginUserId;
	}

	@Transactional(readOnly = true)
	public User findUserById(Long userId) {
		// 유저 정보가 존재하지 않으면 예외를 발생시킴
		return userRepository.findById(userId)
			.orElseThrow(() -> new UserException(UserExceptionType.USER_NOT_FOUND_EXCEPTION)); // 예외 처리
	}
}
