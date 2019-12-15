package com.renchao.server.dao;

import com.renchao.server.model.Metrics;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

public class MetricsDaoTest {
    @Autowired
    MetricsDao dao;
    @Test
    void test(){
        long a = dao.select("test").getCount();
        System.out.println(a);
    }
}
