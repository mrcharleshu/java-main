package com.charles.lib.http;

import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.ssl.SSLContextBuilder;
import org.apache.http.ssl.TrustStrategy;

import javax.net.ssl.SSLContext;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.List;

/**
 * below are the steps for using Apache HttpClient to send GET and POST requests.
 * <p>
 * 1.Create instance of CloseableHttpClient using helper class HttpClients.
 * 2.Create HttpGet or HttpPost instance based on the HTTP request type.
 * 3.Use addHeader method to add required headers such as User-Agent, Accept-Encoding etc.
 * 4.For POST, create list of NameValuePair and add all the form parameters. Then set it to the HttpPost entity.
 * 5.Get CloseableHttpResponse by executing the HttpGet or HttpPost request.
 * 6.Get required details such as status code, error information, response html etc from the response.
 * 7.Finally close the apache HttpClient resource.
 */
public class HttpClientExample {

    private static final String USER_AGENT = "Mozilla/5.0";

    private static final String GET_URL = "http://www.gxnu.edu.cn/default.html";

    private static final String POST_URL = "http://localhost:9090/SpringMVCExample/home";

    public static void main(String[] args) throws IOException {
        sendGET();
        System.out.println("GET DONE");
        //sendPOST();
        //System.out.println("POST DONE");
    }

    private static void sendGET() throws IOException {
        CloseableHttpClient httpClient = HttpClients.createDefault();
        // CloseableHttpClient httpClient = createSSLClientDefault();
        String url = "https://sms.api.ums86.com:9600/sms/Api/Send.do?SpCode=215991&LoginName=zx_gst&Password=gst12345&MessageContent=%B8%D0%D0%BB%C4%FA%CA%B9%D3%C3%D6%C7%C4%DC%B5%E7%B9%DC%BC%D2%A3%AC%C4%FA%B1%BE%B4%CE%B2%D9%D7%F7%B5%C4%D1%E9%D6%A4%C2%EB%CA%C7123124%2C%C8%E7%D3%D0%D2%C9%CE%CA%C7%EB%B2%A6%B4%F2%BF%CD%B7%FE%C8%C8%CF%DF400-166-6326%21&SerialNumber=94324383390924368498&ScheduleTime=&f=1&ExtendAccessNum=&UserNumber=17701618907";
        // HttpGet httpGet = new HttpGet(url);
        HttpGet httpGet = new HttpGet(GET_URL);
        // httpGet.addHeader("User-Agent", USER_AGENT);
        CloseableHttpResponse response = httpClient.execute(httpGet);

        System.out.println("GET Response Status:: " + response.getStatusLine().getStatusCode());
        consume(response.getEntity());
        httpClient.close();
    }

    private static void sendPOST() throws IOException {

        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpPost httpPost = new HttpPost(POST_URL);
        httpPost.addHeader("User-Agent", USER_AGENT);

        List<NameValuePair> urlParameters = new ArrayList<>();
        urlParameters.add(new BasicNameValuePair("userName", "Pankaj Kumar"));

        HttpEntity postParams = new UrlEncodedFormEntity(urlParameters);
        // HttpEntity postParams = new UrlEncodedFormEntity(urlParameters, "UTF-8");
        httpPost.setEntity(postParams);

        CloseableHttpResponse response = httpClient.execute(httpPost);

        System.out.println("POST Response Status:: " + response.getStatusLine().getStatusCode());

        consume(response.getEntity());
        httpClient.close();

    }

    private static void consume(HttpEntity entity) throws IOException {
        if (entity == null) {
            return;
        }
        if (entity.isStreaming()) {
            final InputStream is = entity.getContent();
            if (is != null) {
                BufferedReader reader = new BufferedReader(new InputStreamReader(is));
                String line;
                StringBuffer result = new StringBuffer();
                while ((line = reader.readLine()) != null) {
                    result.append(line);
                }
                reader.close();
                System.out.println(result.toString());
            }
        }
    }

    public static CloseableHttpClient createSSLClientDefault() {
        try {
            SSLContext sslContext = new SSLContextBuilder().loadTrustMaterial(null, new TrustStrategy() {
                //信任所有
                public boolean isTrusted(X509Certificate[] chain,
                        String authType) throws CertificateException {
                    return true;
                }
            }).build();
            SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(sslContext);
            return HttpClients.custom().setSSLSocketFactory(sslsf).build();
        } catch (KeyManagementException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (KeyStoreException e) {
            e.printStackTrace();
        }
        return HttpClients.createDefault();
    }
}