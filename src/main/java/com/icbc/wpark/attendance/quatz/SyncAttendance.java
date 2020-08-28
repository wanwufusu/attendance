package com.icbc.wpark.attendance.quatz;


import com.icbc.wpark.attendance.service.AttendanceService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
@Slf4j
public class SyncAttendance {

    @Autowired
    AttendanceService attendanceService;

    @Scheduled(cron = "0 50 18 * * ?")
    public void syncEverydayAttendance() {
        try {
            log.info(new Date() + "start sync");
            attendanceService.syncSimpleAttendance();
            log.info(new Date() +"sync success");
        } catch (Exception e) {
            log.error(new Date() + e.getMessage(), e);
            e.printStackTrace();
        }
    }
}
