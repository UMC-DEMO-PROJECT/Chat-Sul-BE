package com.chatsul.web.controller;

import com.chatsul.annotation.CurrentMember;
import com.chatsul.apiPayload.ApiResponse;
import com.chatsul.converter.ReservationConverter;
import com.chatsul.domain.Member;
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
            summary = "사용자 예약 API",
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
            @RequestBody @Valid ReservationRequestDTO.MakeReservationRequestDTO request, @CurrentMember Member member) {
        Reservation reservation = reservationCommandService.createReservation(request, venueId, member);
        return ApiResponse.onSuccess(ReservationConverter.toReservationResultDTO(reservation));
    }

    @Operation(summary = "사용자 예약 확인 API",
            description = "사용자가 예약 내역(대관 내역)을 확인하는 API 입니다.<br>"
                    + "status -> WAITING_DEPOSIT: 입금 대기, WAITING_CONFIRMATION: 확정 대기, CONFIRMED: 확정, CANCELLED: 취소")
    @GetMapping("/list")
    public ApiResponse<ReservationResponseDTO.ReservationPreViewListDTO> getReservationList(
            @CurrentMember Member member, @RequestParam("page") Integer page) {
        Page<Reservation> reservationList = reservationQueryService.getReservationList(member, page);
        return ApiResponse.onSuccess(ReservationConverter.reservationPreViewListDTO(reservationList));
    }

    @Operation(summary = "사용자 예약 취소 API",
            description = "사용자가 예약을 취소하는 API 입니다.<br>")
    @DeleteMapping("/cancel/{reservationId}")
    public ApiResponse<String> cancelReservation(
            @PathVariable("reservationId") Long reservationId, @CurrentMember Member member) {
        reservationCommandService.cancelReservation(reservationId, member);
        return ApiResponse.onSuccess("예약이 취소되었습니다.");
    }

    @Operation(summary = "예약 계좌정보 확인 API",
            description = "입금 대기 상태의 예약에 대해 계좌정보를 제공하는 API입니다.<br>")
    @GetMapping("/{reservationId}/account-info")
    public ApiResponse<ReservationResponseDTO.AccountInfoDTO> getReservationAccountInfo(
            @PathVariable("reservationId") Long reservationId, @CurrentMember Member member) {
        ReservationResponseDTO.AccountInfoDTO accountInfo = reservationQueryService.getReservationAccountInfo(reservationId, member);
        return ApiResponse.onSuccess(accountInfo);
    }

    // 사장님 로그인 구현 전까지는 venueId 받음
    @Operation(summary = "사장님 예약 확인 API",
            description = "사장님이 예약 내역을 확인하는 API 입니다.<br>"
                    + "ALL: 모두, CONFIRMED: 확정, WAITING_DEPOSIT: 입금 대기, WAITING_CONFIRMATION: 확정 대기")
    @GetMapping("/business/list/{venueId}")
    public ApiResponse<ReservationResponseDTO.BusinessReservationPreViewListDTO> getBusinessReservationList(
            @PathVariable("venueId") Long venueId, @RequestParam("status") String status, @RequestParam("page") Integer page
    , @CurrentMember Member member) {
        Page<Reservation> reservationList = reservationQueryService.getBusinessReservationList(venueId, status, page, member);
        return ApiResponse.onSuccess(ReservationConverter.businessReservationPreViewListDTO(reservationList));
    }

    @Operation(summary = "사장님 예약 수락 API",
            description = "사장님이 예약을 수락하는 API 입니다.<br>")
    @PatchMapping("/business/{venueId}/accept/{reservationId}")
    public ApiResponse<String> acceptReservation(
            @PathVariable("venueId") Long venueId, @PathVariable("reservationId") Long reservationId, @CurrentMember Member member) {
        reservationCommandService.acceptReservation(reservationId, venueId, member);
        return ApiResponse.onSuccess("예약이 수락되었습니다.");
    }

    @Operation(summary = "사장님 예약 거절 API",
            description = "사장님이 예약을 거절하는 API 입니다.<br>")
    @PatchMapping("/business/{venueId}/reject/{reservationId}")
    public ApiResponse<String> rejectReservation(
            @PathVariable("venueId") Long venueId, @PathVariable("reservationId") Long reservationId, @CurrentMember Member member) {
        reservationCommandService.rejectReservation(reservationId, venueId, member);
        return ApiResponse.onSuccess("예약이 거절되었습니다.");
    }

    @Operation(summary = "사장님 예약 확정 API",
            description = "사장님이 예약을 확정하는 API 입니다.<br>")
    @PatchMapping("/business/{venueId}/confirm/{reservationId}")
    public ApiResponse<String> confirmReservation(
            @PathVariable("venueId") Long venueId, @PathVariable("reservationId") Long reservationId, @CurrentMember Member member) {
        reservationCommandService.confirmReservation(reservationId, venueId, member);
        return ApiResponse.onSuccess("예약이 확정되었습니다.");
    }
}
