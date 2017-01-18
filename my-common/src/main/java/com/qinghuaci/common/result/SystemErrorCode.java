package com.qinghuaci.common.result;


public enum SystemErrorCode implements ErrCode {
    SUCCESS(0, "success", "成功"),
    SERVER_BUSY(-1, "server is busy", "系统繁忙"),
    SYSTEM_ERROR(-2, "system error", "系统错误");

    private int code;
    private String message;
    private String description;

    private SystemErrorCode(int code, String message, String description) {
        this.code = code;
        this.message = message;
        this.description = description;
    }

    public int getCode() {
        return this.code;
    }

    public String getMessage() {
        return this.message;
    }

    public String getDescription() {
        return this.description;
    }

    public int getErrCode() {
        return this.code;
    }

    public String getErrMessage() {
        return this.message;
    }

    public String getErrDescription() {
        return this.description;
    }
}
