package com.icbc.wpark.attendance.util;

import com.alibaba.fastjson.JSONObject;
import com.icbc.wpark.attendance.entity.MessageDto;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class MessageUtils {

    private static String url = "https://wparksdc.dccnet.com.cn:8447/wpark/message/wparkmessage/sendMessage";

    public static boolean sendMessage(MessageDto messageDto) {
        JSONObject messageObject = new JSONObject();
        messageObject.put("content", messageDto.getContent());
        messageObject.put("messageUser", messageDto.getMessageUser());
        messageObject.put("phoneNumber", messageDto.getPhoneNumber());
        messageObject.put("serviceNumber", messageDto.getServiceNumber());
        messageObject.put("messageType", messageDto.getMessageType());
        messageObject.put("createUser", messageDto.getCreateUser());
        String res = HttpUtils.HttpPostWithJson(url, messageObject.toJSONString());
        if ("接口调用失败".equals(res)){
            log.info("发送短信给" + messageDto.getMessageUser() + "失败");
            return false;
        } else {
            log.info("发送短信给" + messageDto.getMessageUser() + "成功");
            return true;
        }
    };

    public static void main(String[] args) {
        MessageDto messageDto = new MessageDto("测试短信外部", "邹睿明", "18826077350", "", "2", "aliyun");
        boolean b = sendMessage(messageDto);
        if (b) {
            System.out.println("发送短信成功");
        } else {
            System.out.println("发送短信失败");
        }
    }
}
