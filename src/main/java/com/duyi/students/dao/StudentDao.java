package com.duyi.students.dao;

import com.duyi.students.domain.Student;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface StudentDao {
    void add(Student student);

    void del(String sno);

    void update(Student student);

    List<Student> findByAll(@Param(value = "appkey") String appkey);

    List<Student> findByPage(@Param(value = "appkey") String appkey,@Param(value = "offset") int offset,@Param(value = "size") int size);

    Student findBySno(@Param(value = "sNo") String sNo);

    int getPageSum(String appkey);
}
