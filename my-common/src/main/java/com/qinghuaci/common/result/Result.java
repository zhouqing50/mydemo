package com.qinghuaci.common.result;
import java.io.Serializable;

public interface Result extends Serializable {
    int getErrCode();

    String getErrMessage();

    String getErrDescription();

    boolean isSuccess();
}
