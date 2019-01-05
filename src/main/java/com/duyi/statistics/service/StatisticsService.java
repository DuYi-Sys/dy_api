package com.duyi.statistics.service;

import com.duyi.statistics.dao.CountDao;
import com.duyi.statistics.domain.Count;
import com.duyi.statistics.domain.DateSum;
import com.duyi.util.TimeUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StatisticsService {
    @Autowired
    CountDao countDao;
    public void addCount(String appkey,String path) {
        Count count = new Count();
        count.setAppkey(appkey);
        count.setPath(path);
        count.setPv(count.getPv() + 1);
        count.setDate(TimeUtil.getDateTime());
        count.setCtime(TimeUtil.getNow());
        countDao.insertCount(count);
    }


    /**
     * 总共使用次数
     * @param appkey
     * @return
     */
    public int getPvCount(String appkey,int begin,int end) {
        return countDao.getPvCount(appkey,begin,end);
    }

    public List<DateSum> getDayCount(String appkey, int begin, int end) {
        return countDao.getDayCount(appkey,begin,end);
    }
}
