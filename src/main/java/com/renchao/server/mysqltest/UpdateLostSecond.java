package com.renchao.server.mysqltest;

import com.renchao.server.dao.UserDao;
import com.renchao.server.model.User;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.transaction.TransactionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.DefaultTransactionDefinition;

/**
 * 第二类更新丢失问题
 * 读未提交会有更新丢失的问题,起初我以为只有读未提交才会产生该问题，但发现所有隔离级别都不能预防该问题。经过学习发现更新丢失分为两类，
 * 这是第二类更新丢失，发生原因是第二次更新覆盖了第一次更新。
 * 举例：事务1取值value（初始值为0，我这里用Value代替），暂停一秒，然后更新为value+50。
 *      事务2取值value，此时还未被修改，仍为0，更新为value+100；两个事务分别对相同值加了50和100但是有先后顺序，会有一个更新被覆盖。
 *      最后值要不是50，要不是100。
 * 这里用了注解式事务。
 */
@Service
public class UpdateLostSecond {
    @Autowired
    UserDao userDao;
    @Transactional(isolation = Isolation.READ_COMMITTED)
    public void func(int i) throws InterruptedException {
        System.out.println("事务+"+i+" Start");
        synchronized (this) {
            String pw = userDao.selectByName("renchao").getValue();
            System.out.println("事务+" + i + " 当前Value：" + pw);
//            Thread.sleep(1000);
            boolean status = userDao.update("renchao", String.valueOf(Integer.valueOf(pw) + 50 * i));
            System.out.println("事务+"+i+"  更新状态："+status);
        }
//        System.out.println("事务+"+i+"  更新后密码："+userDao.selectByName("renchao").getValue());
    }
}
