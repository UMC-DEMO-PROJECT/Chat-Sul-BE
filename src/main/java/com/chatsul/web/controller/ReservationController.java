package com.chatsul.web.controller;

import com.chatsul.apiPayload.ApiResponse;
import com.chatsul.converter.ReservationConverter;
import com.chatsul.domain.Reservation;
import com.chatsul.service.ReservationService.ReservationCommandService;
import com.chatsul.service.ReservationService.ReservationQueryService;
import com.chatsul.web.dto.ReservationRequestDTO;
import com.chatsul.web.dto.ReservationResponseDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/reservation")
@RequiredArgsConstructor
public class ReservationController {

    private final ReservationCommandService reservationCommandService;
    private final ReservationQueryService reservationQueryService;

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
                                            "  \"userId\": 1,\n" +
                                            "  \"phoneNumber\": \"01012345678\",\n" +
                                            "  \"reservationDate\": \"2025-01-08\",\n" +
                                            "  \"reservationTime\": \"20:30\",\n" +
                                            "  \"numberOfGuests\": 2,\n" +
                                            "  \"depositorName\": \"홍길동\"\n" +
                                            "}"
                            )
                    )
            )
    )
    @PostMapping("/{venueId}")
    public ApiResponse<ReservationResponseDTO.ReservationResultDTO> createReservation(
            @PathVariable("venueId") Long venueId,
            @RequestBody @Valid ReservationRequestDTO.MakeReservationRequestDTO request) {
        Reservation reservation = reservationCommandService.createReservation(request, venueId);
        return ApiResponse.onSuccess(ReservationConverter.toReservationResultDTO(reservation));
    }

    @Operation(summary = "예약 확인 API",
            description = "예약 내역(대관 내역)을 확인하는 API 입니다.<br>"
                    + "WAITING_DEPOSIT: 입금 대기, WAITING_CONFIRMATION: 확정 대기, CONFIRMED: 확정, CANCELLED: 취소")
    @GetMapping("/list")
    public ApiResponse<ReservationResponseDTO.ReservationPreViewListDTO> getReservationList(
            @RequestParam("userId") Long userId, @RequestParam("page") Integer page) {
        Page<Reservation> reservationList = reservationQueryService.getReservationList(userId, page);
        return ApiResponse.onSuccess(ReservationConverter.reservationPreViewListDTO(reservationList));
    }
}
