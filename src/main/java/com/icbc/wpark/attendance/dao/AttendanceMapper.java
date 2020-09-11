package com.icbc.wpark.attendance.dao;

import com.icbc.wpark.attendance.entity.SimpleAttendance;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author ruiming_z
 */
@Mapper
@Repository
public interface AttendanceMapper {


    /**
     * 添加简易版的通行证
     * @param simpleAttendance
     * @return
     */
    @Insert("replace into wpark_attendance (user_num, office_type, update_time) values" +
            "(#{userNum}, #{officeType}, now())")
    int insertSimple(SimpleAttendance simpleAttendance);


    /**
     * 批量添加简易版通行证
     * @param simpleAttendances
     * @return
     */
    @Insert({
            "<script>","replace into wpark_attendance (user_num, office_type, update_time) values" ,
            "<foreach collection='list' item='item' index='index' separator=','>",
            "(#{item.userNum}, #{item.officeType}, now())" ,
            "</foreach>",
            "</script>"})
    int insertSimpleBatch(List<SimpleAttendance> simpleAttendances);

    @Select("select office_type from wpark_attendance where user_num = #{userNum}")
    String selectSimpleByUserNum(String userNum);
}
