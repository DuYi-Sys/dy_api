package com.duyi.upload.dao;

import com.duyi.upload.domain.UpfFile;
import org.apache.ibatis.annotations.Param;

public interface UpfFileDao {

    void addUpfFile(UpfFile upfFile);

    UpfFile getUpfFile(@Param("fileName") String fileName);

}
