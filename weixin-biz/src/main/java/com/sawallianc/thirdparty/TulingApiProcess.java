package com.sawallianc.thirdparty;

import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;

/**
 * Created by fingertap on 2017/6/2.
 */
public class TulingApiProcess {
    /**
     * 调用图灵机器人api接口，获取智能回复内容，解析获取自己所需结果
     * @param content
     * @return
     */
    public String getTulingResult(String content) throws Exception{
        String result = new TulingV1().request(content);
        /** 请求失败处理 */
        if(null==result){
            return "对不起，你说的话真是太高深了……";
        }

        try {
            JSONObject json = JSONObject.parseObject(result);
            //以code=100000为例，参考图灵机器人api文档
            if(100000==json.getInteger("code")){
                result = json.getString("text");
            }else{
                result = json.getString("text")+"，请看这里"+json.getString("url");
            }
        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return result;
    }
}
