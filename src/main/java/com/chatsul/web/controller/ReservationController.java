package com.chatsul.web.controller;

import com.chatsul.apiPayload.ApiResponse;
import com.chatsul.converter.ReservationConverter;
import com.chatsul.domain.Reservation;
import com.chatsul.service.ReservationService.ReservationCommandService;
import com.chatsul.web.dto.ReservationRequestDTO;
import com.chatsul.web.dto.ReservationResponseDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/reservation")
@RequiredArgsConstructor
public class ReservationController {

    private final ReservationCommandService reservationCommandService;

    @Operation(
            summary = "예약 API",
            description = "예약(대관) API입니다.<br>",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "예약 요청 데이터",
                    required = true,
                    content = @Content(
                            mediaType = "application/json",
                            examples = @ExampleObject(
                                    name = "예약 요청 예시",
                                    value = "{\n" +
                                            "  \"reservationName\": \"홍길동\",\n" +
                                            "  \"userId\": \"1\",\n" +
                                            "  \"venueId\": \"1\",\n" +
                                            "  \"phoneNumber\": \"01012345678\",\n" +
                                            "  \"reservationDate\": \"2025-1-20\",\n" +
                                            "  \"reservationTime\": \"20:30\",\n" +
                                            "  \"numberOfGuests\": 2,\n" +
                                            "  \"depositorName\": \"홍길동\"\n" +
                                            "}"
                            )
                    )
            )
    )
    @PostMapping("/")
    public ApiResponse<ReservationResponseDTO.ReservationResultDTO> createReservation(@RequestBody ReservationRequestDTO.MakeReservationRequestDTO request) {
        Reservation reservation = reservationCommandService.createReservation(request);
        return ApiResponse.onSuccess(ReservationConverter.toReservationResultDTO(reservation));
    }
}
