package com.duyi.students.controller;

import com.duyi.common.BaseController;
import com.duyi.common.RespStatusEnum;
import com.duyi.students.domain.Student;
import com.duyi.students.service.StudentService1;
import com.duyi.util.RegExUtil;
import com.duyi.util.TimeUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@Controller
@RequestMapping(value = "/api/student")
public class StudentController1 extends BaseController {

    @Autowired
    StudentService1 studentService1;
    @RequestMapping(value = "/addStudent",method = RequestMethod.GET)
    public void addStudent(@RequestParam(name = "appkey") String appkey,
                           @RequestParam(name = "sNo") String sNo,
                           @RequestParam(name = "name") String name,
                           @RequestParam(name = "email") String email,
                           @RequestParam(name = "sex") Integer sex,
                           @RequestParam(name = "birth") Integer birth,
                           @RequestParam(name = "phone") String phone,
                           @RequestParam(name="address") String address,
                           HttpServletResponse resp) throws Exception {


        if (!RegExUtil.match("^[0-9]{4,16}$", sNo)) {
            writeResult(resp, RespStatusEnum.FAIL.getValue(), "学号必须为4-16位的数字组成", null);
            return;
        }

        if(name == null || "".equals(name)) {
            writeResult(resp, RespStatusEnum.FAIL.getValue(), "学生姓名不能为空", null);
            return;

        }

        if(!RegExUtil.match("^[A-Za-z\\d]+([-_.][A-Za-z\\d]+)*@([A-Za-z\\d]+[-.])+[A-Za-z\\d]{2,5}$",email)) {
            writeResult(resp, RespStatusEnum.FAIL.getValue(), "邮箱格式不正确", null);
            return;
        }


        if (birth == 0) {
            writeResult(resp, RespStatusEnum.FAIL.getValue(), "出生年份必须大于0", null);
            return;
        }

        if(!RegExUtil.match("^[0-9]{11}$",phone)){
            writeResult(resp, RespStatusEnum.FAIL.getValue(), "请输入正确的11位手机号", null);
            return;
        }

        if (address == null || "".equals(address)) {
            writeResult(resp, RespStatusEnum.FAIL.getValue(), "请填写住址", null);
            return;
        }

        Student student = new Student();
        student.setsNo(sNo);
        student.setName(name);
        student.setEmail(email);
        student.setSex(sex);
        student.setBirth(birth);
        student.setPhone(phone);
        student.setAddress(address);
        student.setAppkey(appkey);
        student.setCtime(TimeUtil.getNow());
        student.setUtime(TimeUtil.getNow());
        StudentService1.addStudentStatusEnum result = studentService1.addStudent(student);
        writeResult(resp,result.getStatusEnum().getValue(),result.getMsg(),null);
    }

    /**
     * 查询所有appkey的学生
     * @param appkey
     * @param resp
     * @throws IOException
     */
    @RequestMapping(value = "/findAll",method = RequestMethod.GET)
    public void findAll(@RequestParam(name = "appkey") String appkey, HttpServletResponse resp) throws IOException {

        List<Student> findAll = studentService1.findAll(appkey);
        writeResult(resp,RespStatusEnum.SUCCESS.getValue(),"",findAll);

    }

}
