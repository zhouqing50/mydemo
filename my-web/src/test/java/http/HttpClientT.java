package http;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpStatus;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.ssl.SSLContexts;
import org.apache.http.util.EntityUtils;
import org.junit.Test;

import javax.net.ssl.SSLContext;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

/**
 * Description:
 * Tuser: zhouq
 * Date: 2016/12/14
 */


@Slf4j
public class HttpClientT {
    private static String HEADER_USER_AGENT = "Mozilla/5.0 (Windows NT 6.3; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/51.0.2704.103 Safari/537.36";

    PoolingHttpClientConnectionManager cm = null;
    private SSLContext sslcontext = SSLContexts.createDefault();
    private CloseableHttpClient httpClient = HttpClients.custom()
            .setSSLContext(sslcontext)
            .setConnectionManager(cm)
            .setSSLHostnameVerifier(NoopHostnameVerifier.INSTANCE).build();


    @Test
    public void testGetUrl40(){
        for (int i = 0; i < 40; i++) {
            getUrl();
        }
    }

    @Test
    public void getUrl(){
        HttpGet httpGet = null;
        CloseableHttpResponse response = null;
        byte[] imageBytes;
        long startTime = System.currentTimeMillis();
        long step1Time = 0;
        String sourceImageUrl = "http://wx.qlogo.cn/mmopen/fIe9Dur8icxWJH075eEUO0rr1j7ib5SPx75LRmLkvom0CazyicmiauBAyHgy0gdvw7Ql2cRscWNzWz7F9Q2Wib7AoG4yNeeTWkaOZ/0";
        try {
            //剔除微信和QQ的webp请求参数，目前无法解析谷歌webp格式图片
            httpGet = new HttpGet(new URI(sourceImageUrl));
            RequestConfig requestConfig = RequestConfig.custom()
                    .setSocketTimeout(1000)
                    .setConnectTimeout(2000)
                    .setConnectionRequestTimeout(5000).build();

            httpGet.setConfig(requestConfig);
            httpGet.setHeader("Tuser-Agent", HEADER_USER_AGENT);
            response = httpClient.execute(httpGet);
            if (response.getStatusLine().getStatusCode() != HttpStatus.SC_OK) {
                log.error("response' status code is not 200.sourceImageUrl[{}],response[{}]", sourceImageUrl, response);
            }
            imageBytes = EntityUtils.toByteArray(response.getEntity());
            if (imageBytes.length > 5242880) {
                log.error("image size more than 5M.sourceImageUrl[{}]", sourceImageUrl);
            }
            step1Time = System.currentTimeMillis();
            log.info("httpGetUrl step1 httpClient.execute, time={}, size={}", step1Time-startTime, imageBytes.length);
        } catch (URISyntaxException e) {
            log.error("image uri check failed.sourceImageUrl[{}],e[{}]", sourceImageUrl, e);
        } catch (IOException e) {
            log.error("httpClient.execute() failed.sourceImageUrl[{}],e[{}]", sourceImageUrl, e);
        } finally {
            if (null != response) {
                try {
                    response.close();
                } catch (IOException e) {
                }
            }
            if (null != httpGet) {
                httpGet.releaseConnection();
            }
        }
    }
}
