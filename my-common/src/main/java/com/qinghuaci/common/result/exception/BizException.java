package com.qinghuaci.common.result.exception;

import com.qinghuaci.common.result.ErrCode;
import com.qinghuaci.common.result.Result;

public class BizException extends RuntimeException {
    private static final long serialVersionUID = -7949170734224838097L;
    private int errCode;
    private String errMessage;
    private String errDescription;

    public BizException(ErrCode codeEnum) {
        this(codeEnum.getErrCode(), codeEnum.getErrMessage(), codeEnum.getErrDescription());
    }

    public BizException(ErrCode error, Throwable cause) {
        super(error.getErrCode() + " : " + error.getErrMessage() + " : " + error.getErrDescription(), cause);
        this.errCode = error.getErrCode();
        this.errMessage = error.getErrMessage();
        this.errDescription = error.getErrDescription();
    }

    public BizException(int errCode, Throwable cause, String errDescription) {
        super(errCode + " : " + errDescription + " : " + cause.getLocalizedMessage(), cause);
        this.errCode = errCode;
        this.errMessage = errDescription;
        this.errDescription = errDescription;
    }

    public BizException(int errCode, Throwable cause, String errMessage, String errDescription) {
        super(errCode + " : " + errMessage + " : " + errDescription + " : " + cause.getLocalizedMessage(), cause);
        this.errCode = errCode;
        this.errMessage = errMessage;
        this.errDescription = errDescription;
    }

    public BizException(int errCode, String errDescription) {
        this(errCode, "", errDescription);
    }

    public BizException(int errCode, String errMessage, String errDescription) {
        super(errCode + " : " + errMessage + " : " + errDescription);
        this.errCode = errCode;
        this.errMessage = errMessage;
        this.errDescription = errDescription;
    }

    public BizException(Result result) {
        this(result.getErrCode(), result.getErrMessage(), result.getErrDescription());
    }

    public BizException(int errCode, Result result, String errDescription) {
        super(errCode + " : " + result.getErrCode() + "+" + result.getErrMessage() + "+" + result.getErrDescription() + " : " + errDescription);
        this.errCode = errCode;
        this.errDescription = errDescription;
    }

    public int getErrCode() {
        return this.errCode;
    }

    public void setErrCode(int errCode) {
        this.errCode = errCode;
    }

    public String getErrMessage() {
        return this.errMessage;
    }

    public void setErrMessage(String errMessage) {
        this.errMessage = errMessage;
    }

    public String getErrDescription() {
        return this.errDescription;
    }

    public void setErrDescription(String errDescription) {
        this.errDescription = errDescription;
    }
}
