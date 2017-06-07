package com.sawallianc.web;

import com.alibaba.fastjson.JSONObject;
import com.sawallianc.service.UserService;
import com.sawallianc.thirdparty.TulingApiProcess;
import com.sawallianc.util.SHAUtils;
import com.sawallianc.xml.FormatXmlProcess;
import com.sawallianc.xml.ReceiveXmlEntity;
import com.sawallianc.xml.ReceiveXmlProcess;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.web.bind.annotation.*;

import java.security.DigestException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@RestController
public class ComputeController {

    private final static Logger logger = LoggerFactory.getLogger(ComputeController.class);
    @Autowired
    private DiscoveryClient client;
    @Autowired
    private UserService service;
    @RequestMapping(value = "/add" ,method = RequestMethod.GET)
    public Integer add(@RequestParam Integer a, @RequestParam Integer b) {
        ServiceInstance instance = client.getLocalServiceInstance();
        Integer r = a + b;
        service.update();
        logger.info("/add, host:" + instance.getHost() + ", service_id:" + instance.getServiceId() + ", result:" + r);
        return r;
    }
    @RequestMapping(value = "/hello" ,method = RequestMethod.GET)
    public String hello(@RequestParam String jsoncallback) {
        String r = "Hello World";
        JSONObject json = new JSONObject();
        json.put("name",r);
        json.put("age",12);
        json.put("description","I'm xiaoming");

        return json.toString();
    }
    @RequestMapping(value = "/weixindd" ,method = RequestMethod.GET)
    public String weixin(@RequestParam String signature,@RequestParam String timestamp,@RequestParam String nonce,
                         @RequestParam String echostr){
        JSONObject json = new JSONObject();
        json.put("signature",signature);
        json.put("timestamp",timestamp);
        json.put("nonce",nonce);
        json.put("echostr",echostr);
        System.out.println("------json:"+json);
        List<String> list = new ArrayList<String>(3);
        list.add(timestamp);
        list.add(nonce);
        list.add("fingertap");
        Collections.sort(list);
        String result = null;
        try {
            result = SHAUtils.SHA1(list.get(0) + list.get(1) + list.get(2));
            if(StringUtils.equalsIgnoreCase(signature,result)){
                System.out.println("=================验证成功"+echostr);
                return echostr;
            }
        } catch (DigestException e) {
            e.printStackTrace();
        }
        return "";
    }

    @RequestMapping(value = "/weixin" ,method = RequestMethod.POST)
    public String dealMessage(@RequestBody String xml) throws Exception{
        /** 解析xml数据 */
        ReceiveXmlEntity xmlEntity = new ReceiveXmlProcess().getMsgEntity(xml);

        /** 以文本消息为例，调用图灵机器人api接口，获取回复内容 */
        String result;
        if("text".endsWith(xmlEntity.getMsgType())){
            result = new TulingApiProcess().getTulingResult(xmlEntity.getContent());
        }else{
            result = "我只看懂文字呀！";
        }

        /** 此时，如果用户输入的是“你好”，在经过上面的过程之后，result为“你也好”类似的内容
         *  因为最终回复给微信的也是xml格式的数据，所有需要将其封装为文本类型返回消息
         * */
        result = new FormatXmlProcess().formatXmlAnswer(xmlEntity.getFromUserName(), xmlEntity.getToUserName(), result);
        return result;
    }


}