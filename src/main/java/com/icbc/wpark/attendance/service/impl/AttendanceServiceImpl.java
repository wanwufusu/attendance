package com.icbc.wpark.attendance.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.icbc.wpark.attendance.dao.AttendanceMapper;
import com.icbc.wpark.attendance.entity.MessageDto;
import com.icbc.wpark.attendance.entity.SimpleAttendance;
import com.icbc.wpark.attendance.service.AttendanceService;
import com.icbc.wpark.attendance.util.AESUtils;
import com.icbc.wpark.attendance.util.HttpUtils;
import com.icbc.wpark.attendance.util.MessageUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;


@Service
@Slf4j
public class AttendanceServiceImpl implements AttendanceService {

    @Autowired
    AttendanceMapper attendanceMapper;

    @Override
    public void syncSimpleAttendance() throws Exception {
        log.info(new Date() + "    start to sync----------------    ");
//        String url = "https://wparksdc-kf.dccnet.com.cn:448/wpark_test/vote/attendance/getSimpleAttendance";
        String url = "https://wparksdc.dccnet.com.cn:8447/wpark/vote/attendance/getSimpleAttendance";
        List<MessageDto> messageDtos = new ArrayList<>();
        JSONObject jsonObject = new JSONObject();
//        jsonObject.put("passDate", "2020-09-15");
        String res = HttpUtils.HttpPostWithJson(url, jsonObject.toJSONString());
        if ("接口调用失败".equals(res)) {
            String content = "同步失败，请联系管理员处理";
            log.error("接口调用失败，请检查http接口是否通！");
            messageDtos.add(new MessageDto(content, "庞良", "18666130202", "", "2", "aliyun"));
            messageDtos.add(new MessageDto(content, "周永深", "13544901768", "", "2", "aliyun"));
            messageDtos.add(new MessageDto(content, "黄卫", "13702948308", "", "2", "aliyun"));
            messageDtos.add(new MessageDto(content, "李硕", "18088826225", "", "2", "aliyun"));
            messageDtos.add(new MessageDto(content, "伍玉霞", "15992697981", "", "2", "aliyun"));
            for (MessageDto messageDto : messageDtos) {
                MessageUtils.sendMessage(messageDto);
            }
            return;
        }
        Map<String, Object> parse = (Map<String, Object>) JSON.parse(res);
        List data = (List) parse.get("data");
        List<SimpleAttendance> attendances = new ArrayList<>();
        if (data != null && data.size() > 0) {
            for (Object dto : data) {
                Map<String, String> mapDto = (Map<String, String>) JSON.parse(dto.toString());
                SimpleAttendance simpleAttendance = new SimpleAttendance();
                simpleAttendance.setOfficeType(mapDto.get("officeType"));
                simpleAttendance.setUserNum(AESUtils.AESEncode(mapDto.get("userNum")));
                attendances.add(simpleAttendance);
            }
            attendanceMapper.insertSimpleBatch(attendances);
        }
        //同步成功发短信
        String content = "任务执行完毕，共计" + attendances.size() + "条通行证记录同步成功至【阿里云】！";
        messageDtos.add(new MessageDto(content, "庞良", "18666130202", "", "2", "aliyun"));
        messageDtos.add(new MessageDto(content, "周永深", "13544901768", "", "2", "aliyun"));
        messageDtos.add(new MessageDto(content, "黄卫", "13702948308", "", "2", "aliyun"));
        messageDtos.add(new MessageDto(content, "李硕", "18088826225", "", "2", "aliyun"));
        messageDtos.add(new MessageDto(content, "伍玉霞", "15992697981", "", "2", "aliyun"));
        for (MessageDto messageDto : messageDtos) {
            MessageUtils.sendMessage(messageDto);
        }
        log.info(new Date() + "    Sync end! There are " + attendances.size() + "   records has been insert.");
    }

    @Override
    public String getSimpleType(String userNum) {
        return attendanceMapper.selectSimpleByUserNum(userNum);
    }
}
