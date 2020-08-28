package com.icbc.wpark.attendance.service;

public interface AttendanceService {

    void syncSimpleAttendance() throws Exception;

    String getSimpleType(String userNum);
}
