package com.hebing.until;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import java.util.Date;

/**
 * Created by hebing on 2019/7/23.
 */
public class WeChatUntil {
    //url 验证得token
    public static final String TOKEN = "hebing";
    //appid
    public static final String APPID = "wx70493ae58daff0d7";
    //secret
    public static final String SECRET = "b31ed05f4c733d883c06a0801c92d965";
    //创建公众号得菜单的接口
    public static final String CREATE_MENU_URL = "https://api.weixin.qq.com/cgi-bin/menu/create?access_token=ACCESS_TOKEN";
    //获取access_token的接口
    public static final String GET_ACCESS_TOKEN = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=APPID&secret=APPSECRET";
    //缓存的access_token
    public static String accessToken;
    //access_token的过期时间
    public static long expiresTime;

    /**
     * 获取access_token
     */
    public static String getAccessToken() throws Exception {
        if(accessToken == null || expiresTime < new Date().getTime()){
            //获取access_token
            String url = GET_ACCESS_TOKEN.replace("APPID",APPID).replace("APPSECRET",SECRET);
            JSONObject parse = HttpUtils.getUrlInfo(url, "GET",null);

            //缓存access_token
            accessToken = parse.getString("access_token");
            //重新请求微信获取access_token
            System.out.println("重新获取access_token:"+accessToken);
            //获取过期时间
            long expires_in = parse.getLong("expires_in");
            expiresTime = new Date().getTime() + (expires_in - 60) * 1000;
        }
        return accessToken;
    }

    /**
     * 创建公众号的菜单
     */
    public static void createMenu(String menu) throws Exception {

        HttpUtils.getUrlInfo(CREATE_MENU_URL.replace("ACCESS_TOKEN",getAccessToken()),"POST",menu);
    }










    public static void main(String[] args) throws Exception {
        String jsonStr = "{\n" +
                "     \"button\":[\n" +
                "     {    \n" +
                "          \"type\":\"click\",\n" +
                "          \"name\":\"开班信息\",\n" +
                "          \"key\":\"classinfo\"\n" +
                "      },\n" +
                "     {    \n" +
                "          \"type\":\"click\",\n" +
                "          \"name\":\"校区地址\",\n" +
                "          \"key\":\"address\"\n" +
                "      },\n" +
                "      {\n" +
                "           \"name\":\"学科介绍\",\n" +
                "           \"sub_button\":[\n" +
                "           {    \n" +
                "               \"type\":\"view\",\n" +
                "               \"name\":\"Java课程\",\n" +
                "               \"url\":\"http://www.wolfcode.cn/zt/java/index.html\"\n" +
                "            },\n" +
                "           {    \n" +
                "               \"type\":\"view\",\n" +
                "               \"name\":\"Python课程\",\n" +
                "               \"url\":\"http://www.wolfcode.cn/python.html\"\n" +
                "            }]\n" +
                "       }]\n" +
                " }";
        createMenu(jsonStr);
    }

}
