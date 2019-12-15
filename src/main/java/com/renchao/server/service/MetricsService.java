package com.renchao.server.service;

import com.alibaba.dubbo.config.annotation.Service;
import com.renchao.server.api.MetricsApi;
import com.renchao.server.dao.MetricsDao;
import com.renchao.server.model.Metrics;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Service
@Component
public class MetricsService implements MetricsApi {
    @Autowired
    MetricsDao dao;

    @Override
    public long callCount(String apiName, long count) {
        System.out.println("统计接口名："+apiName);
        Metrics metrics = dao.select(apiName);
        if(metrics==null){
            System.out.println("该接口不存在");
            return -1;
        }
        long r = metrics.getCount()+count;
        dao.updateCountById(r,metrics.getId());
        System.out.println("该接口调用次数:"+r);

        return r;
    }
}
