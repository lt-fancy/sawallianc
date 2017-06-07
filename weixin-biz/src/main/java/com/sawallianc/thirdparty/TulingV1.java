package com.sawallianc.thirdparty;

import com.alibaba.fastjson.JSONObject;
import com.sawallianc.constant.ConfigConstant;
import com.sawallianc.util.AESUtils;
import com.sawallianc.util.MD5Utils;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.io.IOException;

/**
 * Created by fingertap on 2017/6/2.
 */
public class TulingV1 {
    public String request(String content) throws Exception{
        HttpPost httpPost = new HttpPost(ConfigConstant.TULING_API_URL);
        httpPost.setHeader("Content-Type", "application/json; charset=UTF-8");
        JSONObject json = new JSONObject();
        json.put("key",ConfigConstant.TULING_API_KEY);
        json.put("timestamp",String.valueOf(System.currentTimeMillis()));
        json.put("data",genParam(content));
        StringEntity entity = new StringEntity(json.toJSONString(),"utf-8");
        httpPost.setEntity(entity);
        String result;
        try {
            HttpResponse response = HttpClients.createDefault().execute(httpPost);
            if(ConfigConstant.HTTP_OK.intValue() == response.getStatusLine().getStatusCode()){
                result = EntityUtils.toString(response.getEntity(),"utf-8");
                return result;
            }
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "对不起，你说的话真是太高深了……";
    }

    private static String genParam(String content){
        String keyParam = ConfigConstant.TULING_SECRET+String.valueOf(System.currentTimeMillis())+
                ConfigConstant.TULING_API_KEY;
        String key = MD5Utils.MD5(keyParam);
        AESUtils aesUtils = new AESUtils(key);
        JSONObject json = new JSONObject();
        json.put("info",content);
        String data = aesUtils.encrypt(json.toJSONString());
        return data;
    }

}
