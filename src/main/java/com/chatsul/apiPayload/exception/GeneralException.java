package com.chatsul.apiPayload.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import com.chatsul.apiPayload.code.BaseErrorCode;
import com.chatsul.apiPayload.code.ErrorReasonDTO;

@Getter
@AllArgsConstructor
public class GeneralException extends RuntimeException {

    private BaseErrorCode code;

    public ErrorReasonDTO getErrorReason() {
        return this.code.getReason();
    }

    public ErrorReasonDTO getErrorReasonHttpStatus(){
        return this.code.getReasonHttpStatus();
    }
}