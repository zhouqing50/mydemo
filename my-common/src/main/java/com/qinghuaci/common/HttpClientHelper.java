package com.qinghuaci.common;


import lombok.extern.slf4j.Slf4j;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Description:
 * User: zhouq
 * Date: 2016/12/28
 */
@Slf4j
public class HttpClientHelper {

    public static String get(String uri) {
        CloseableHttpClient httpclient = HttpClients.createDefault();
        try {
            HttpGet httpget = new HttpGet(uri);

            log.debug("Executing get request start url={}", uri);
            CloseableHttpResponse response = httpclient.execute(httpget);
            try {
                httpget.abort();
                String respStr = EntityUtils.toString(response.getEntity(), "UTF-8");
                log.debug("Executing get request end status={}, response.getEntity={}", response.getStatusLine(), respStr);
                return respStr;
            } finally {
                try {
                    response.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                httpclient.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    public static String post(String uri, String pars) {
        CloseableHttpClient httpclient = HttpClients.createDefault();
        try {
            HttpPost httpPost = new HttpPost(uri);
            httpPost.setEntity(new StringEntity(pars, "utf-8"));
            log.debug("Executing post request start uri={}, pars={}", uri, pars);
            CloseableHttpResponse response = httpclient.execute(httpPost);
            try {
                String respStr = EntityUtils.toString(response.getEntity(), "UTF-8");
                log.debug("Executing post request end status={}, response.getEntity={}", response.getStatusLine(), respStr);
                return respStr;
            } finally {
                try {
                    response.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                httpclient.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    public static String post(String uri, Map<String, String> map) {
        CloseableHttpClient httpclient = HttpClients.createDefault();
        try {
            HttpPost httpPost = new HttpPost(uri);
            List<NameValuePair> nvps = new ArrayList<>();
            map.keySet().forEach(key -> nvps.add(new BasicNameValuePair(key, map.get(key))));
            httpPost.setEntity(new UrlEncodedFormEntity(nvps));
            log.debug("Executing post request start uri={}, map={}", uri, JsonKit.object2json(map));
            CloseableHttpResponse response = httpclient.execute(httpPost);
            try {
                String respStr = EntityUtils.toString(response.getEntity(), "UTF-8");
                log.debug("Executing post request end status={}, response.Entity={}", response.getStatusLine(), respStr);
                return respStr;
            } finally {
                try {
                    response.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                httpclient.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }
}
