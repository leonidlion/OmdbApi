package com.dev.leo.omdbapi.enums;

public enum RequestCode {
    REQ_FILM_DETAIL(0x01);

    int code;

    RequestCode(int code){
        this.code = code;
    }

    public int getCode() {
        return code;
    }
}
