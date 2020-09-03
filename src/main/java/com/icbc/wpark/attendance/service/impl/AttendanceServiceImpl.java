package com.icbc.wpark.attendance.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.icbc.wpark.attendance.dao.AttendanceMapper;
import com.icbc.wpark.attendance.entity.SimpleAttendance;
import com.icbc.wpark.attendance.service.AttendanceService;
import com.icbc.wpark.attendance.util.AESUtils;
import com.icbc.wpark.attendance.util.HttpUtils;
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
        log.info(new Date() + "    start to sync-----------------");
        String url = "https://wparksdc-kf.dccnet.com.cn:448/wpark_test/vote/attendance/getSimpleAttendance";
        JSONObject jsonObject = new JSONObject();
        String res = HttpUtils.HttpPostWithJson(url, jsonObject.toJSONString());
        Map<String, Object> parse = (Map<String, Object>) JSON.parse(res);
        List data = (List) parse.get("data");
//        SimpleAttendance simpleAttendance = new SimpleAttendance();
        List<SimpleAttendance> attendances = new ArrayList<>();
        for (Object dto : data) {
            Map<String, String> mapDto = (Map<String, String>) JSON.parse(dto.toString());
            SimpleAttendance simpleAttendance = new SimpleAttendance();
            simpleAttendance.setOfficeType(mapDto.get("officeType"));
            simpleAttendance.setUserNum(AESUtils.AESEncode(mapDto.get("userNum")));
            attendances.add(simpleAttendance);
        }
        attendanceMapper.insertSimpleBatch(attendances);
        log.info(new Date() + "    Sync end! There are " + attendances.size() + "records has been insert.");
    }

    @Override
    public String getSimpleType(String userNum) {
        return attendanceMapper.selectSimpleByUserNum(userNum);
    }
}
