package com.icbc.wpark.attendance;

import com.icbc.wpark.attendance.dao.AttendanceMapper;
import com.icbc.wpark.attendance.entity.SimpleAttendance;
import com.icbc.wpark.attendance.service.AttendanceService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class AttendanceApplicationTests {

    @Autowired
    AttendanceService attendanceService;

    @Autowired
    AttendanceMapper attendanceMapper;

    @Test
    void contextLoads() throws Exception {
        attendanceService.syncSimpleAttendance();
    }

}
