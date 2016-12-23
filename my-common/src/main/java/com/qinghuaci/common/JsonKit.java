package com.qinghuaci.common;

import com.google.gson.Gson;

/**
 * 转json 工具
 *
 * @author zenglb
 * @date 2015年9月2日
 */
public class JsonKit {

    /**
     * object 2 json
     *
     * @param object
     * @return
     */
    public static final String object2json(Object object) {
        if (null == object) {
            return null;
        }
        Gson gson = new Gson();
        return gson.toJson(object);
    }

    /**
     * json2 object
     *
     * @param json
     * @param clazz
     * @return
     */
    public static final <T> T json2object(String json, Class<T> clazz) {
        if (null == json || "".equals(json) || null == clazz) {
            return null;
        }
        Gson gson = new Gson();
        return gson.fromJson(json, clazz);
    }
}
