package com.sawallianc.constant;

/**
 * Created by fingertap on 2017/6/2.
 */
public class ConfigConstant {

    /**
     * 微信相关配置
     */
    public static final String APP_ID = "wx74ffc2a9d0acc2cf";
    public static final String APP_SECRET = "8d49ad80235f4881a2af9a93e0fb01f4";
    public static final String WEIXIN_GET_TOKEN_URL = "https://api.weixin.qq.com/cgi-bin/token";
    public static final String WEIXIN_GET_IPS = "https://api.weixin.qq.com/cgi-bin/getcallbackip";

    /**
     * 微信创建自定义菜单接口
     */
    public static final String WEIXIN_POST_CREATE = "https://api.weixin.qq.com/cgi-bin/menu/create";
    /**
     * 微信查询自定义菜单接口
     */
    public static final String WEIXIN_GET_QUERY = "https://api.weixin.qq.com/cgi-bin/menu/get";
    /**
     * 微信删除自定义菜单接口
     */
    public static final String WEIXIN_GET_DELETE = "https://api.weixin.qq.com/cgi-bin/menu/delete";
    /**
     * 微信查询当前菜单配置接口
     */
    public static final String WEIXIN_GET_QUERYCURRENTMENU = "https://api.weixin.qq.com/cgi-bin/get_current_selfmenu_info";

    /**
     * HTTP请求配置
     */
    public static final Integer HTTP_OK = 200;

    /**
     * 图灵api key
     */
    public static final String TULING_API_URL = "http://www.tuling123.com/openapi/api";
    public static final String TULING_API_KEY = "3f4d71ba867b4d5eb41395a8ea7bb839";
    public static final String TULING_API_URL_V2 = "http://openapi.tuling123.com/openapi/api/v2";

    public static final String TULING_SECRET = "981a315dd3acb9e0";

    /**
     * 搬瓦工相关api接口
     */
    public static final String BANWAGONG_VEID = "540692";

    public static final String BANWAGONG_API_KEY = "private_sjZMh6Ph1bo29S11q4Q8ZIBv";

    public static final String BANWAGONG_API_URL = "https://api.64clouds.com/v1/getServiceInfo";
}
