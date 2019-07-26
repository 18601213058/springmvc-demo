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
        System.out.println(msg.toString());
        //创建响应消息对象
        OutMsgEntity outMsgEntity = new OutMsgEntity();
        //把原来的发送方设置为接受方 也把原来的接受方设置为发送方
        outMsgEntity.setToUserName(msg.getFromUserName());
        System.out.println("发送用户FromUserName:"+msg.getFromUserName());
        outMsgEntity.setFromUserName(msg.getToUserName());
        System.out.println("发给用户ToUserName:"+msg.getToUserName());
        //获取接受消息的类型
        String msgType = msg.getMsgType();
        outMsgEntity.setMsgType(msgType);
        //设置创建时间
        outMsgEntity.setCreateTime(msg.getCreateTime());
        String outContent = null;
        //根据类型设置不同的消息数据
        if("text".equals(msgType)){
            //用户发送的内容
            String inContent = msg.getContent();

            if(inContent.contains("谢谢")){
                outContent = "不客气";
            }else if(inContent.contains("地址")){
                outContent = "北京朝阳";
            }else {
                outContent = inContent;
            }
            outMsgEntity.setContent(outContent);
        }else if("image".equals(msgType)){
            outMsgEntity.setMediaId(new String[]{msg.getMediaId()});
        }else if("event".equals(msgType)){
            if("subscribe".equals(msg.getEvent())){
                outMsgEntity.setContent("欢迎关注！！！");
                outMsgEntity.setMsgType("text");
            }else if("CLICK".equals(msg.getEvent())){
                String key = msg.getEventKey();
                if("classinfo".equals(key)){
                    outContent = "上海Java基础班第05期于2018/05/10开班\n" +
                            "广州Java基础班第24期于2018/04/02开班";
                }else if("address".equals(key)){
                    outContent = "北京校区：北京昌平区沙河镇万家灯火装饰城2楼8077号\n" +
                            "广州校区：广州市天河区棠下涌东路大地工业区D栋六楼\n" +
                            "上海校区：上海市青浦区华新镇华隆路1777号E通世界商务园华新园A座4楼402";
                }
                outMsgEntity.setContent(outContent);
                outMsgEntity.setMsgType("text");
            }

        }
        return outMsgEntity;
    }

}
