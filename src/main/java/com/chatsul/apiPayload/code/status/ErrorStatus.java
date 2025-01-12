package com.chatsul.apiPayload.code.status;

import org.springframework.http.HttpStatus;

import lombok.AllArgsConstructor;
import lombok.Getter;

import com.chatsul.apiPayload.code.BaseErrorCode;
import com.chatsul.apiPayload.code.ErrorReasonDTO;

@Getter
@AllArgsConstructor
public enum ErrorStatus implements BaseErrorCode {

	_INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "COMMON500", "서버 에러, 관리자에게 문의 바랍니다."),
	_BAD_REQUEST(HttpStatus.BAD_REQUEST, "COMMON400", "잘못된 요청입니다."),
	_UNAUTHORIZED(HttpStatus.UNAUTHORIZED, "COMMON401", "인증이 필요합니다."),
	_FORBIDDEN(HttpStatus.FORBIDDEN, "COMMON403", "금지된 요청입니다."),

	// 예약 2일 전 취소 불가
	CANCEL_RESERVATION_BEFORE_2DAYS(HttpStatus.BAD_REQUEST, "CANCEL4001", "예약 2일 전에는 취소가 불가능합니다."),

	// Member
	MEMBER_NOT_FOUND(HttpStatus.NOT_FOUND, "MEMBER404", "멤버를 찾을 수 없습니다."),

	// Jwt
	EMPTY_TOKEN(HttpStatus.UNAUTHORIZED, "TOKEN400", "헤더에 토큰이 비어 있습니다."),
	INVALID_TOKEN(HttpStatus.UNAUTHORIZED, "TOKEN401", "토큰이 유효하지 않습니다."),
	REFRESH_TOKEN_NOT_FOUND(HttpStatus.NOT_FOUND, "TOKEN404", "리프레시 토큰을 찾을 수 없습니다."),
	INVALID_REFRESH_TOKEN(HttpStatus.BAD_REQUEST, "TOKEN402", "토큰이 만료되었습니다. 재로그인해주세요."),
	INVALID_HEADER_FORMAT(HttpStatus.BAD_REQUEST, "TOKEN403", "헤더 형식이 올바르지 않습니다."),
	EXPIRED_TOKEN(HttpStatus.UNAUTHORIZED, "TOKEN404", "토큰이 만료되었습니다."),

	// OAuth
	OAUTH_TOKEN_FAIL(HttpStatus.BAD_REQUEST,"OAUTH400","토큰 변경 실패"),
	OAUTH_USER_INFO_FAIL(HttpStatus.NOT_FOUND, "OAUTH401","사용자 정보를 가져오지 못했습니다"),
	INVALID_PROVIDER(HttpStatus.BAD_REQUEST, "OAUTH402", "올바르지 않은 플랫폼입니다."),

	// For test
	TEMP_EXCEPTION(HttpStatus.BAD_REQUEST, "TEMP4001", "이거는 테스트");

	private final HttpStatus httpStatus;
	private final String code;
	private final String message;

	@Override
	public ErrorReasonDTO getReason() {
		return ErrorReasonDTO.builder()
			.message(message)
			.code(code)
			.isSuccess(false)
			.build();
	}

	@Override
	public ErrorReasonDTO getReasonHttpStatus() {
		return ErrorReasonDTO.builder()
			.message(message)
			.code(code)
			.isSuccess(false)
			.httpStatus(httpStatus)
			.build()
			;
	}
}