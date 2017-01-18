package com.qinghuaci.common.result;


import com.qinghuaci.common.result.exception.BizException;

import java.io.Serializable;

public class BaseResult<T> implements Result, Serializable {
    private static final long serialVersionUID = 6039777006120900996L;
    private int errCode;
    private String errMessage;
    private String errDescription;
    protected T result;

    public BaseResult(T result) {
        this();
        this.result = result;
    }

    public BaseResult(ErrCode codeEnum) {
        this.codeEnum(codeEnum);
    }

    public BaseResult() {
        this.codeEnum(SystemErrorCode.SUCCESS);
    }

    public BaseResult(BizException e) {
        this.bizException(e);
    }

    public T getResult() {
        return this.result;
    }

    public void setResult(T result) {
        this.result = result;
    }

    public <A> A codeEnum(ErrCode codeEnum) {
        this.errCode = codeEnum.getErrCode();
        this.errMessage = codeEnum.getErrMessage();
        this.errDescription = codeEnum.getErrDescription();
        return (A) this;
    }

    public <A> A bizException(BizException bizException) {
        this.errCode = bizException.getErrCode();
        this.errMessage = bizException.getErrMessage();
        this.errDescription = bizException.getErrDescription();
        return (A) this;
    }

    public int getErrCode() {
        return this.errCode;
    }

    public String getErrDescription() {
        return this.errDescription;
    }

    public String getErrMessage() {
        return this.errMessage;
    }

    public String toString() {
        return "result [errCode=" + this.errCode + ", errMessage=" + this.errMessage + ", errDescription=" + this.errDescription + ", result=" + this.result + "]";
    }

    public boolean isSuccess() {
        return 0 == this.getErrCode();
    }
}
