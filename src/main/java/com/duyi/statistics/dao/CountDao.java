package com.duyi.statistics.dao;

import com.duyi.statistics.domain.Count;
import com.duyi.statistics.domain.DateSum;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface CountDao {

    void insertCount( Count count);

    Integer getPvCount(@Param("appkey") String appkey, @Param("begin") int begin, @Param("end") int end);

    List<DateSum> getDayCount(@Param("appkey") String appkey, @Param("begin") int begin, @Param("end") int end);
}