package com.qinghuaci.common;

import java.util.UUID;

/**
 * @author lambo
 * @date on 2016/01/07
 */
public final class IdGenerateUtils {
    private IdGenerateUtils() {}

    public static final String generateUUID() {
        String uuid = UUID.randomUUID().toString();
        return uuid.replace("-", "");
    }

    public static final String nativeUUID() {
        return UUID.randomUUID().toString();
    }
}
