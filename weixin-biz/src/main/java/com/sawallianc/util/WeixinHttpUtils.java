package com.sawallianc.util;

import com.alibaba.fastjson.JSONObject;
import com.sawallianc.constant.ConfigConstant;
import org.apache.commons.io.IOUtils;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.TrustStrategy;
import org.apache.http.conn.ssl.X509HostnameVerifier;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.ssl.SSLContextBuilder;

import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLException;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocket;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.List;

/**
 * Created by fingertap on 2017/6/3.
 */
public class WeixinHttpUtils {
    public static String getWeixinAccessToken() throws Exception{
        HttpClient client = createSSLInsecureClient();
        String param = "grant_type=client_credential&appid="+ ConfigConstant.APP_ID+
                "&secret="+ConfigConstant.APP_SECRET;
        HttpGet httpGet = new HttpGet(ConfigConstant.WEIXIN_GET_TOKEN_URL+"?"+param);
        HttpResponse response = client.execute(httpGet);
        String result = IOUtils.toString(response.getEntity().getContent(),"UTF-8");
        System.out.println("============result:"+result);
        JSONObject json = JSONObject.parseObject(result);
        result = json.getString("access_token");
        System.out.println("============result:"+result);
        return result;
    }

    public static String getServerIps() throws Exception{
        HttpClient client = createSSLInsecureClient();
        String param = "access_token="+getWeixinAccessToken();
        HttpGet httpGet = new HttpGet(ConfigConstant.WEIXIN_GET_IPS+"?"+param);
        HttpResponse response = client.execute(httpGet);
        String result = IOUtils.toString(response.getEntity().getContent(),"UTF-8");
        JSONObject json = JSONObject.parseObject(result);
        List<String> ipLists =(List<String>) json.get("ip_list");
        System.out.println(ipLists.size());
        System.out.println("============result:"+result);
        return result;
    }


    public static String getBanWaGongServerInfo() throws Exception{
        HttpClient client = createSSLInsecureClient();
        String param = "veid="+ConfigConstant.BANWAGONG_VEID+"&api_key="+ConfigConstant.BANWAGONG_API_KEY;
        HttpGet httpGet = new HttpGet(ConfigConstant.BANWAGONG_API_URL+"?"+param);
        HttpResponse response = client.execute(httpGet);
        String result = IOUtils.toString(response.getEntity().getContent(),"UTF-8");
        JSONObject json = JSONObject.parseObject(result);
        System.out.println("============result:"+json);
        return result;
    }

    public static void main(String[] args) throws Exception{
        getBanWaGongServerInfo();
    }

    /**
     * 创建 SSL连接
     * @return
     * @throws GeneralSecurityException
     */
    private static CloseableHttpClient createSSLInsecureClient() throws GeneralSecurityException {
        try {
            SSLContext sslContext = new SSLContextBuilder().loadTrustMaterial(null, new TrustStrategy() {
                public boolean isTrusted(X509Certificate[] chain, String authType) throws CertificateException {
                    return true;
                }
            }).build();

            SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(sslContext, new X509HostnameVerifier() {

                @Override
                public boolean verify(String arg0, SSLSession arg1) {
                    return true;
                }

                @Override
                public void verify(String host, SSLSocket ssl)
                        throws IOException {
                }

                @Override
                public void verify(String host, X509Certificate cert)
                        throws SSLException {
                }

                @Override
                public void verify(String host, String[] cns,
                                   String[] subjectAlts) throws SSLException {
                }

            });

            return HttpClients.custom().setSSLSocketFactory(sslsf).build();

        } catch (GeneralSecurityException e) {
            throw e;
        }
    }
}
