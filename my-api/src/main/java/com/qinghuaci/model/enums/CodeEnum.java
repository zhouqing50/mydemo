package com.qinghuaci.model.enums;

import com.qinghuaci.common.result.ErrCode;

/**
 * Description:
 * User: zhouq
 * Date: 2017/1/17
 */
public enum CodeEnum implements ErrCode {

    /**
     * 成功
     */
    SUCCESS(0, "SUCCESS", "成功"),


    /**
     * 系统异常
     */
    SYSTEM_EXCEPTION(70001, "SYSTEM_EXCEPTION", "系统异常"),

    /**
     * 返回值为空
     */
    RESULT_IS_NULL(70002, "RESULT_IS_NULL", "返回值为空"),

    /**
     * 参数不合法
     */
    PARAM_ILLEGAL_EXCEPTION(70400, "PARAM_ILLEGAL_EXCEPTION", "参数不合法"),

    /**
     * 应用不存在
     */
    APP_NOT_EXISTS(75001, "APP_NOT_EXISTS", "应用不存在");


    private final int errCode;

    private final String errMsg;

    private final String description;

    /**
     * 构造方法
     *
     * @param errCode     返回code
     * @param description 返回消息
     */
    private CodeEnum(int errCode, String errMsg, String description) {
        this.errCode = errCode;
        this.errMsg = errMsg;
        this.description = description;
    }


    public int getErrCode() {
        return errCode;
    }

    public String getErrMessage() {
        return errMsg;
    }

    public String getErrDescription() {
        return description;
    }

    public String getErrMsg() {
        return errMsg;
    }

    public String getDescription() {
        return description;
    }

    /**
     * 通过枚举<code>code</code>获得枚举。
     *
     * @param code
     * @return
     */
    public static CodeEnum getByCode(int code) {
        for (CodeEnum status : values()) {
            if (status.errCode == code) {
                return status;
            }
        }
        return null;
    }

    public String asString() {
        return this.errCode + " : " + this.errMsg + " : " + this.description;
    }
}

