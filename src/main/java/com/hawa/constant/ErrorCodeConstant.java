package com.hawa.constant;

import lombok.Getter;

@Getter
public enum ErrorCodeConstant {
    OK(0,"Ok"),
    //BAD_REQUEST(400,HttpStatus.BAD_REQUEST),
    INVALID_CREDENTIAL(602,"Please Provide Valid Credential"),
    PASSWORD_QUALITY_NOT_MET(603,"PLease Provide the Password that Contains Atleast 8 Characters With One Uppercase One Lowercase, " +
            "One Digit And One Special Character"),
    ACCESS_DENIED(604,"Please Provide Valid %s"),
    ALREADY_EXISTS(604,"Please Provide Another %s"),
    INVALID_DATA(604,"Please Provide Valid %s"),
    NOT_AUTHENTICATED(401,"Please Provide Valid Token"),
    NOT_FOUND(404,"Please Provide Valid %s"),
    INTERNAL_SERVER_ERROR(500,"Please Contact Support Team");

    private final int code;
    private final String message;

    private ErrorCodeConstant(int code, String message){
        this.code=code;
        this.message=message;
    }
}

