package com.chatsul.apiPayload.exception.handler;

import com.chatsul.apiPayload.code.BaseErrorCode;
import com.chatsul.apiPayload.exception.GeneralException;

public class TempHandler extends GeneralException {

    public TempHandler(BaseErrorCode errorCode) {
        super(errorCode);
    }
}