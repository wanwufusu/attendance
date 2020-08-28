package com.icbc.wpark.attendance.controller;

import com.icbc.wpark.attendance.common.Result;
import com.icbc.wpark.attendance.entity.SimpleAttendance;
import com.icbc.wpark.attendance.service.AttendanceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/attendance")
public class AttendanceController {

    @Autowired
    AttendanceService attendanceService;

    @GetMapping("/getMySimpleAttendance")
    public Result<String> getMySimpleAttendance(@RequestParam String userNum) {
        String simpleType = attendanceService.getSimpleType(userNum);
        if (StringUtils.isEmpty(simpleType)) {
            return Result.error("暂未查到该员工的入园信息");
        } else {
            return Result.ok(simpleType, "查询成功");
        }
    }
}
