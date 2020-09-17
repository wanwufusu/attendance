package com.icbc.wpark.attendance.entity;

import lombok.Data;

@Data
public class MessageDto {

    //短信内容
    private String content;

    //收件人姓名
    private String messageUser;

    //收信人手机号
    private String phoneNumber;

    //单号，可不填
    private String serviceNumber;

    //类型，一般填2
    private String messageType;

    //发信人
    private String createUser;

    public MessageDto() {
    }

    public MessageDto(String content, String messageUser, String phoneNumber, String serviceNumber, String messageType, String createUser) {
        this.content = content;
        this.messageUser = messageUser;
        this.phoneNumber = phoneNumber;
        this.serviceNumber = serviceNumber;
        this.messageType = messageType;
        this.createUser = createUser;
    }
}
