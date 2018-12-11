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
//                e.printStackTrace();
                return addStudentStatusEnum.UNKNOW_ERROR;
            }

        }

    }

    /**
     * 查找所有appkey下的学生
     * @param appkey
     * @return
     */
    public List<Student> findAll(String appkey) {
        return studentDao.findByAll(appkey);
    }

    /**
     * 分页
     * @param appkey
     * @param page
     * @param size
     * @return
     */
    public List<Student> findByPage(String appkey,Integer page,Integer size) {
        Integer offset = (page - 1) * size ;
        return studentDao.findByPage(appkey,offset,size);
    }

    /**
     * 获取总条数
     * @return
     */
    public int count() {
        return studentDao.getPageSum();
    }

    /**
     * 删除一条学生信息
     * @param sNo
     * @return
     */
    public delStudentStatusEnum delBySno(String sNo) {
        try {
            studentDao.del(sNo);
            return delStudentStatusEnum.SUCCESS;
        } catch (Exception e) {
            Student student = studentDao.findBySno(sNo);
            if(student == null) {
                return delStudentStatusEnum.NOT_FOND_STUDENT;
            } else {
                return delStudentStatusEnum.UNKNOW_ERROR;
            }
        }

    }

    /**
     * 修改一条学生记录
     * @param student
     * @return
     */
    public updateStudentStatusEnum updateStudent(Student student) {
        try {
            studentDao.update(student);
            return updateStudentStatusEnum.SUCCESS;
        } catch (Exception e) {
            Student student1 = studentDao.findBySno(student.getsNo());
            if(student1 == null) {
                return updateStudentStatusEnum.NOT_FOND_STUDENT;
            } else {
                return  updateStudentStatusEnum.UNKNOW_ERROR;
            }
        }
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

    public enum delStudentStatusEnum{
        NOT_FOND_STUDENT(RespStatusEnum.FAIL, "未找到该学生"), SUCCESS(RespStatusEnum.SUCCESS, "删除成功"), UNKNOW_ERROR(RespStatusEnum.FAIL, "未知错误");
        private RespStatusEnum statusEnum;
        private String msg;

        delStudentStatusEnum(RespStatusEnum statusEnum, String msg) {
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

    public enum updateStudentStatusEnum{
        NOT_FOND_STUDENT(RespStatusEnum.FAIL, "未找到该学生"), SUCCESS(RespStatusEnum.SUCCESS, "修改成功"), UNKNOW_ERROR(RespStatusEnum.FAIL, "未知错误");
        private RespStatusEnum statusEnum;
        private String msg;

        updateStudentStatusEnum(RespStatusEnum statusEnum, String msg) {
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
