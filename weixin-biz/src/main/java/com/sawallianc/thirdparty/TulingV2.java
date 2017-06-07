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
public class TulingV2 {
    public String request(String content,boolean flag) throws Exception{
        HttpPost httpPost = new HttpPost(ConfigConstant.TULING_API_URL_V2);
        httpPost.setHeader("Content-Type", "application/json; charset=UTF-8");
        JSONObject json = new JSONObject();
        StringEntity entity;
        if(flag){
            json.put("key",ConfigConstant.TULING_API_KEY);
            json.put("timestamp",String.valueOf(System.currentTimeMillis()));
            json.put("data",genParam(content,flag));
            entity = new StringEntity(json.toJSONString(),"utf-8");
        }else{
//            entity = new StringEntity(genParam(content,false),"utf-8");
            String data = "{\"perception\": {\"inputText\": {\"text\": \"附近的酒店\"},\"selfInfo\": {\"location\": {\"city\": \"北京\",\"latitude\": \"39.45492\",\"longitude\": \"119.239293\",\"nearest_poi_name\": \"上地环岛南\",\"province\": \"北京\",\"street\": \"信息路\"},}},\"userInfo\": {\"apiKey\": \"3f4d71ba867b4d5eb41395a8ea7bb839\",\"userId\": 1}}";
            entity = new StringEntity(data,"utf-8");
        }
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

    private static String genParam(String content,boolean flag) {
        String keyParam = ConfigConstant.TULING_SECRET+String.valueOf(System.currentTimeMillis())+
                ConfigConstant.TULING_API_KEY;
        String key = MD5Utils.MD5(keyParam);
        AESUtils aesUtils = new AESUtils(key);
        JSONObject json = new JSONObject();
        json.put("text",content);
        JSONObject json2 = new JSONObject();
        json2.put("apiKey",ConfigConstant.TULING_API_KEY);
        json2.put("userId",1);
        JSONObject userInfo = new JSONObject();
        userInfo.put("userInfo",json2);
        JSONObject inputText = new JSONObject();
        inputText.put("inputText",json);
        JSONObject location = new JSONObject();
        JSONObject selfInfo = new JSONObject();
        JSONObject perception = new JSONObject();
        location.put("city","SD");
        selfInfo.put("location",location);
        inputText.put("selfInfo",selfInfo);
        perception.put("perception",inputText);
        perception.put("userInfo",json2);
        String result = perception.toJSONString();
        System.out.println(result);
        String data = aesUtils.encrypt(result);
        if(flag){
           return data;
        }
        return result;
    }
}
