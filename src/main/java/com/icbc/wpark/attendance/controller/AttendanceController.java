package com.icbc.wpark.attendance.controller;

import com.icbc.wpark.attendance.common.Result;
import com.icbc.wpark.attendance.entity.AttendanceReqDto;
import com.icbc.wpark.attendance.entity.SimpleAttendance;
import com.icbc.wpark.attendance.service.AttendanceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

/**
 * @author ruiming_z
 */
@RestController
@RequestMapping("/attendance")
public class AttendanceController {

    @Autowired
    AttendanceService attendanceService;

    /**
     * 获取简易版通行证
     * @param reqDto
     * @return
     */
    @PostMapping("/getMySimpleAttendance")
    public Result<String> getMySimpleAttendance(@RequestBody AttendanceReqDto reqDto) {
        String simpleType = attendanceService.getSimpleType(reqDto.getUserNum());
        if (StringUtils.isEmpty(simpleType)) {
            return Result.error("暂未查到该员工的入园信息");
        } else {
            return Result.ok(simpleType, "查询成功");
        }
    }
}
