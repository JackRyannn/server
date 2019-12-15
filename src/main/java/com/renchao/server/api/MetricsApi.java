package com.renchao.server.api;


public interface MetricsApi {
    /**
     * 统计接口历史调用次数，并更新数据库
     * @param apiName 需要统计的接口名
     * @param count 距上次统计到现在调用的次数
     * @return 最新调用次数
     */
    long callCount(String apiName, long count);
}
