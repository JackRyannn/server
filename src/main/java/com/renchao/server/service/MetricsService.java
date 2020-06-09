package com.renchao.server.service;

import com.alibaba.dubbo.config.annotation.Service;
import com.renchao.server.api.MetricsApi;
import com.renchao.server.dao.MetricsDao;
import com.renchao.server.model.Metrics;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.concurrent.atomic.AtomicLong;
//executes 进行并发限流控制
@Service(executes = 10)
@Component
public class MetricsService implements MetricsApi {
    @Autowired
    MetricsDao dao;
    AtomicLong totalCount = new AtomicLong(0);

    @Override
    public long callCount(String apiName, long count) {
        Metrics metrics = dao.select(apiName);
        if (metrics == null) {
            System.out.println("该接口不存在");
            return -1;
        }
//        如果totalCount没有更新，查询数据库更新
        if (totalCount.longValue() <= 0) {
            System.out.println("统计接口名：" + apiName);
            totalCount.set(metrics.getCount());
        }
//        totalCount 自增1
        totalCount.getAndIncrement();
//        更新到数据库
        dao.updateCountById(totalCount.longValue(), metrics.getId());
//        这里只保证最终一致性，因为在自增后可能别的线程也自增了几次，所以打印结果不是当前线程的totalCount，但最终结果是一致的。
        System.out.println("该接口调用次数:" + totalCount.longValue());

        return totalCount.longValue();
    }
}
