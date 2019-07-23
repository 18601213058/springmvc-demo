package com.hebing.control;

import com.hebing.until.SecurityUtils;
import com.hebing.until.WeChatUntil;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Arrays;

/**
 * Created by hebing on 2019/7/23.
 */
@Controller
public class WXControl {
    @RequestMapping(value = "wechat",method = RequestMethod.GET)
    @ResponseBody
    public String checkToken(String signature,String timestamp,String nonce,String echostr) throws Exception{
        //将token、timestamp、nonce三个参数进行字典序排序
        // 2）将三个参数字符串拼接成一个字符串进行sha1加密
        // 3）开发者获得加密后的字符串可与signature对比，标识该请求来源于微信
        String[] str = {WeChatUntil.TOKEN,timestamp,nonce};
        Arrays.sort(str);
        StringBuffer stringBuffer = new StringBuffer();
        for (String s : str){
            stringBuffer.append(s);
        }
        String mySignature = SecurityUtils.shaEncode(stringBuffer.toString());
        if(signature.equals(mySignature)){
            System.out.println("接入成功");
            return echostr;
        }
        System.out.println("接入失败");
        return null;
    }

}
