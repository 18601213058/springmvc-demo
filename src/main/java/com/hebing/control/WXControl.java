package com.hebing.control;

import com.hebing.domain.InMsgEntity;
import com.hebing.domain.OutMsgEntity;
import com.hebing.until.SecurityUtils;
import com.hebing.until.WeChatUntil;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Arrays;

/**
 * Created by hebing on 2019/7/23.
 */
@Controller
public class WXControl {
    /**
     * 微信公众号设置  服务器地址 和token
     * @param signature
     * @param timestamp
     * @param nonce
     * @param echostr
     * @return
     * @throws Exception
     */
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

    /**
     * 接受微信服务器发来的消息
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "wechat",method = RequestMethod.POST)
    @ResponseBody
    public Object handleMessage(@RequestBody InMsgEntity msg) {
        //创建响应消息对象
        OutMsgEntity outMsgEntity = new OutMsgEntity();
        //把原来的发送方设置为接受方 也把原来的接受方设置为发送方
        outMsgEntity.setToUserName(msg.getFromUserName());
        outMsgEntity.setFromUserName(msg.getToUserName());
        //获取接受消息的类型
        String msgType = msg.getMsgType();
        outMsgEntity.setMsgType(msgType);
        //设置创建时间
        outMsgEntity.setCreateTime(msg.getCreateTime());
        //根据类型设置不同的消息数据
        if("text".equals(msgType)){
            outMsgEntity.setContent(msg.getContent());
        }else if("image".equals(msgType)){
            outMsgEntity.setMediaId(new String[]{msg.getMediaId()});
        }
        return outMsgEntity;
    }

}
