package com.chatsul.web.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.chatsul.annotation.CurrentMember;
import com.chatsul.apiPayload.ApiResponse;
import com.chatsul.domain.Member;
import com.chatsul.service.MemberService.MemberCommandService;
import com.chatsul.web.dto.MemberRequestDTO;
import com.chatsul.web.dto.MemberResponseDTO;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/members")
@RequiredArgsConstructor
public class MemberController {

	private final MemberCommandService memberCommandService;

	@PostMapping("/signup")
	@Operation(summary = "일반 회원가입 API", description = "일반 유저로 회원가입하는 경우 사용하는 API입니다. (소셜 로그인 X)",
		requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
			description = "회원가입 요청 데이터",
			required = true,
			content = @Content(
				mediaType = "application/json",
				examples = @ExampleObject(
					name = "회원가입 요청 예시",
					value = "{\n" +
						"  \"name\": \"홍길동\",\n" +
						"  \"phoneNumber\": \"01012345678\",\n" +
						"  \"email\": \"test@test.com\",\n" +
						"  \"password\": \"test123!\"\n" +
						"}"
				)
			)
		))
	public ApiResponse<String> signUp(@RequestBody @Valid MemberRequestDTO.JoinDTO dto) {
		memberCommandService.joinMember(dto);
		return ApiResponse.onSuccess(null);
	}

	@PostMapping("/signup/social")
	@Operation(summary = "소셜 회원가입 추가정보 입력 API", description = "소셜 로그인 후 추가 정보가 필요한 경우 사용하는 API입니다.",
		requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
			description = "소셜 회원가입 요청 데이터",
			required = true,
			content = @Content(
				mediaType = "application/json",
				examples = @ExampleObject(
					name = "소셜 회원가입 요청 예시",
					value = "{\n" +
						"  \"name\": \"홍길동\",\n" +
						"  \"phoneNumber\": \"01012345678\"\n" +
						"}"
				)
			)
		))
	public ApiResponse<String> signUpSocial(@CurrentMember Member member,
		@RequestBody @Valid MemberRequestDTO.JoinSocialDTO dto) {
		memberCommandService.joinSoicalMember(dto, member);
		return ApiResponse.onSuccess(null);
	}

	@PostMapping("/login")
	@Operation(summary = "로그인 API", description = "로그인 API입니다. 로그인 성공시 accessToken과 사용자 권한인 role이 반환됩니다. <br />"
		+ "accessToken은 로그인이 필요한 서비스의 경우 Authorization 헤더에 'Bearer (accessToken)' 형식으로 담아서 보내주세요. <br />")
	public ApiResponse<MemberResponseDTO.LoginSuccessDTO> login(@RequestBody @Valid MemberRequestDTO.LoginDTO dto,
		HttpServletResponse response) {
		return ApiResponse.onSuccess(memberCommandService.login(dto, response));
	}

	@GetMapping("/info/socialLogin")
	@Operation(summary = "소셜로그인 설명 API", description = "소셜 로그인 과정 설명입니다. <br />"
		+ "1. (백엔드배포주소)/oauth2/authorization/(kakao 또는 naver)로 연결 <br />"
		+ "2. 소셜 로그인 성공 시 (프론트엔드배포주소)/login/social?access_token=(accessToken)&role=(role) 주소로 파라미터를 가지고 리다이렉트됩니다. (주소는 필요시 변경가능합니다!) <br />"
		+ "2 - 1. 사장인 경우 (프론트엔드배포주소)/login/social?access_token=(accessToken)&role=(role)&venueIds=(id)&venueIds=(id)와 같이 리스트 형식으로 가게 정보가 전달됩니다. <br />"
		+ "---- accessToken은 로그인이 필요한 서비스의 경우 Authorization 헤더에 'Bearer (accessToken)' 형식으로 담아서 보내주세요. <br />"
		+ "3. role이 TEMP인 경우 소셜회원가입 추가 정보 기입 화면으로 이동 <br />"
		+ "4. 그 외의 role인 경우 정상적으로 서비스 이용")
	public ApiResponse<String> socialLoginInfo() {
		return ApiResponse.onSuccess("ok");
	}

}
