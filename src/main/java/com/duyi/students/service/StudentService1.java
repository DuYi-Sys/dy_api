package com.duyi.students.service;

import com.duyi.common.RespStatusEnum;
import com.duyi.students.dao.StudentDao;
import com.duyi.students.domain.Student;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StudentService1 {
    @Autowired
    StudentDao studentDao;

    /**
     * 添加一个学生
     * @param student
     * @return
     */
    public addStudentStatusEnum addStudent(Student student) {
        try{
            studentDao.add(student);
            return addStudentStatusEnum.SUCCESS;
        } catch (Exception e){
            Student student1 = studentDao.findBySno(student.getsNo());
            if(student1 != null) {
                return addStudentStatusEnum.EXIST_USER_NAME;
            } else {
                e.printStackTrace();
                return addStudentStatusEnum.UNKNOW_ERROR;
            }

        }

    }


    public List<Student> findAll(String appkey) {
        return studentDao.findByAll(appkey);
    }

    public enum addStudentStatusEnum{
        EXIST_USER_NAME(RespStatusEnum.FAIL, "该学生已经存在"), SUCCESS(RespStatusEnum.SUCCESS, "添加成功"), UNKNOW_ERROR(RespStatusEnum.FAIL, "未知错误");
        private RespStatusEnum statusEnum;
        private String msg;

        addStudentStatusEnum(RespStatusEnum statusEnum, String msg) {
            this.statusEnum = statusEnum;
            this.msg = msg;
        }

        public RespStatusEnum getStatusEnum() {
            return statusEnum;
        }

        public String getMsg() {
            return msg;
        }
    }

}
