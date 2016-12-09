package com.qinghuaci.utils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializer;

import java.text.DateFormat;
import java.util.Date;

public class JsonHttpMessageConverterFsImpl extends org.springframework.http.converter.json.GsonHttpMessageConverter {

    public JsonHttpMessageConverterFsImpl() {
        super();
        GsonBuilder gb = new GsonBuilder();
        gb.registerTypeAdapter(Date.class, (JsonSerializer<Date>) (src, typeOfSrc, context) -> new JsonPrimitive(src.getTime())).setDateFormat(DateFormat.LONG);
        Gson gson = gb.create();
        setGson(gson);
    }
}
