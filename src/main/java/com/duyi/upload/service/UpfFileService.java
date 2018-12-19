package com.duyi.upload.service;

import com.duyi.upload.dao.UpfFileDao;
import com.duyi.upload.domain.UpfFile;
import com.duyi.util.TimeUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
public class UpfFileService {

    @Autowired
    UpfFileDao upfFileDao;

    public void upLoadFile(MultipartFile file, long length, String appkey, String fileName) throws IOException {

        UpfFile upfFile = new UpfFile();
        upfFile.setLength(length);
        upfFile.setFileName(fileName);
        upfFile.setCtime(TimeUtil.getNow());
        upfFile.setAppkey(appkey);
        upfFile.setFile(file.getBytes());

        upfFileDao.addUpfFile(upfFile);

    }

    public UpfFile getUpfFileByFileName(String fileName) {
        return upfFileDao.getUpfFile(fileName);
    }


}
