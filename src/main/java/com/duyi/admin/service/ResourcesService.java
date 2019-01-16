package com.duyi.admin.service;

import com.duyi.admin.dao.ResourcesDao;
import com.duyi.admin.domain.Resources;
import com.duyi.common.RespStatusEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ResourcesService {
    @Autowired
    ResourcesDao resourcesDao;

    public ResourcesService.AddResourcesStuates addResources(Resources resources) {
        try {
            resourcesDao.addResources(resources);
            return AddResourcesStuates.SUCCESS;
        } catch (Exception e) {
            Resources r = resourcesDao.queryByPowerIdAndURL(resources.getUrl(),resources.getPowerId());
            if(r != null) {
                return AddResourcesStuates.EXIST_RESOURCES;
            } else {
                e.printStackTrace();
                return AddResourcesStuates.UNKNOWN_ERROR;
            }
        }
    }

    public List<Resources> queryBypowerId(int powerId){
        return resourcesDao.queryBypowerId(powerId);
    }

    public enum AddResourcesStuates {
        EXIST_RESOURCES(RespStatusEnum.FAIL, "已有该资源"), SUCCESS(RespStatusEnum.SUCCESS, "添加成功"), UNKNOWN_ERROR(RespStatusEnum.FAIL,"未知错误");

        private RespStatusEnum statusEnum;
        private String msg;

        AddResourcesStuates(RespStatusEnum statusEnum, String msg) {
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

