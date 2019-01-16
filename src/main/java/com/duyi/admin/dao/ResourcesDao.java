package com.duyi.admin.dao;

import com.duyi.admin.domain.Resources;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ResourcesDao {
    void addResources(Resources resources);
    Resources queryByPowerIdAndURL(@Param("url") String url, @Param("powerId") int powerId);
    List<Resources> queryBypowerId(@Param("powerId") int powerId);

}
