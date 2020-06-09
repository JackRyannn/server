package com.renchao.server.mysqltest;

import com.renchao.server.dao.UserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

/**
 * 脏读
 * 读未提交会出现，更高隔离级别不会出现
 * 举例：事务1和事务2对value分别增加50，但事务1先+50，事务2再读取value后增加50。不幸的是事务1出了问题需要回滚，那么最后应该只加了50。
 * 但因为脏读导致+100，这是错误的。
 */
@Service
public class UnRepeatableRead {
    @Autowired
    UserDao userDao;

    @Transactional(isolation = Isolation.READ_COMMITTED)
    public void func(int i) throws InterruptedException {
        System.out.println("事务：" + i + " Start");
        String pw = userDao.selectByName("renchao").getValue();
        System.out.println("事务：" + i + " 第一次查询 当前Value：" + pw);

        if (i == 1) {
            Thread.sleep(2000);
            System.out.println("事务：" + i + " 第二次查询 当前Value" + userDao.selectByName("renchao").getValue());
        } else if (i == 2) {
            boolean status = userDao.update("renchao", String.valueOf(Integer.valueOf(pw) + 50));
            System.out.println("事务：" + i + " 更新后Value：" + userDao.selectByName("renchao").getValue());
        }
//        System.out.println("事务+"+i+"  更新后密码："+userDao.selectByName("renchao").getValue());
    }
}