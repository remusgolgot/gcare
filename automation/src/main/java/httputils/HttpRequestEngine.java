package httputils;
import org.apache.http.*;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.conn.HttpHostConnectException;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLContexts;
import org.apache.http.conn.ssl.TrustStrategy;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultRedirectStrategy;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.protocol.HttpContext;
import org.apache.http.util.EntityUtils;

import javax.net.ssl.SSLContext;
import java.io.IOException;

public class HttpRequestEngine {

    private HttpResponse response;
    private HttpClient httpClient;
    private String responsePayload;
    private static PoolingHttpClientConnectionManager poolingClientConnectionManager;

    static {
        TrustStrategy acceptingTrustStrategy = (cert, authType) -> true;
        SSLContext sslContext = null;
        try {
            sslContext = SSLContexts.custom().loadTrustMaterial(null, acceptingTrustStrategy).build();
        } catch (Exception e) {
            e.printStackTrace();
        }
        SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(sslContext, NoopHostnameVerifier.INSTANCE);

        Registry<ConnectionSocketFactory> socketFactoryRegistry =
                RegistryBuilder.<ConnectionSocketFactory> create()
                        .register("https", sslsf)
                        .register("http", new PlainConnectionSocketFactory())
                        .build();
        poolingClientConnectionManager = new PoolingHttpClientConnectionManager(socketFactoryRegistry);
        poolingClientConnectionManager.setDefaultMaxPerRoute(20);
    }

    public HttpRequestEngine() {
        httpClient = HttpClients.custom().
                setConnectionManager(poolingClientConnectionManager).
                setRedirectStrategy(new DefaultRedirectStrategy() {
                    @SuppressWarnings({"MethodReturnAlwaysConstant", "RefusedBequest"})
                    @Override
                    public boolean isRedirected(HttpRequest request, HttpResponse response, HttpContext context) {
                        return false;
                    }
                }).build();
    }

    public void sendHttpPost(String url)
            throws IOException {
        HttpPost postMethod = new HttpPost(url);
        try {
            response = httpClient.execute(postMethod);
        } finally {
            after(postMethod);
        }
    }

    public HttpResponse sendHttpPost(String url, String body) throws IOException {
        HttpPost postMethod = new HttpPost(url);

        try {
            StringEntity entity;
            entity = new StringEntity(body, "UTF-8");
            entity.setContentEncoding("UTF-8");
            entity.setContentType("application/json");
            postMethod.setEntity(entity);
            response = httpClient.execute(postMethod);
            return response;
        } finally {
            after(postMethod);
        }
    }

    public String sendHttpGet(String url) throws IOException {
        HttpGet getMethod = new HttpGet(url);
        try {
            response = httpClient.execute(getMethod);
            return EntityUtils.toString(response.getEntity(), "UTF-8");
        } catch (HttpHostConnectException e) {
            return e.getMessage();
        } finally {
            after(getMethod);
        }
    }

    public int getResponseCode(HttpResponse httpResponse) {
        int responseCode = -1;
        if ((httpResponse != null) && (httpResponse.getStatusLine() != null)) {
            responseCode = httpResponse.getStatusLine().getStatusCode();
        }
        return responseCode;
    }

    private void after(HttpRequestBase httpRequestBase) throws IOException {
        httpRequestBase.releaseConnection();
    }
}
