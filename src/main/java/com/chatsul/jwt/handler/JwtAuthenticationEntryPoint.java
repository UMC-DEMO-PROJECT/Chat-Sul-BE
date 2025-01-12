package com.chatsul.jwt.handler;

import java.io.IOException;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import com.chatsul.apiPayload.ApiResponse;
import com.chatsul.apiPayload.code.status.ErrorStatus;
import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {

	@Override
	public void commence(HttpServletRequest request, HttpServletResponse response,
		AuthenticationException authException) throws
		IOException {
		response.setContentType("application/json; charset=UTF-8");
		response.setStatus(401);

		ApiResponse<Object> errorResponse = ApiResponse.onFailure(
			ErrorStatus._UNAUTHORIZED.getCode(),
			ErrorStatus._UNAUTHORIZED.getMessage(),
			null
		);

		ObjectMapper mapper = new ObjectMapper();
		mapper.writeValue(response.getOutputStream(), errorResponse);
	}
}