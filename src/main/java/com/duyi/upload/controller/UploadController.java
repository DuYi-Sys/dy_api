package com.duyi.upload.controller;

import com.duyi.common.BaseController;
import com.duyi.common.RespStatusEnum;
import com.duyi.upload.domain.UpfFile;
import com.duyi.upload.service.UpfFileService;
import com.duyi.util.TimeUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Controller
@RequestMapping(value = "/api/upf")
public class UploadController extends BaseController {

    @Autowired
    UpfFileService upfFileService;

    @RequestMapping(value = "/uploadFile",method = RequestMethod.POST)
    public void uploadFile(@RequestParam("appkey") String appkey, @RequestParam("file") CommonsMultipartFile file, HttpServletResponse resp) throws IOException {

        String fileName = file.getOriginalFilename();

        System.out.println(fileName);

        String fn = appkey + TimeUtil.getNowTime();
        upfFileService.upLoadFile(file, file.getSize(), appkey, fn);

        writeResult(resp, RespStatusEnum.SUCCESS.getValue(), "OK", fn);
    }

    @RequestMapping(value = "/getFile",method = RequestMethod.GET)
    public void getFile(@RequestParam("appkey") String appkey, @RequestParam("fileName") String fileName, HttpServletResponse resp) throws IOException {

        UpfFile upfFile = upfFileService.getUpfFileByFileName(fileName);
        if (upfFile != null) {
            resp.getOutputStream().write(upfFile.getFile());
        } else {
            writeResult(resp, RespStatusEnum.FAIL.getValue(), "未找到", null);
        }


    }
}
