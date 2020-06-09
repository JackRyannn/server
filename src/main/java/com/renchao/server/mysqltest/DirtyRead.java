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
 *      但因为脏读导致+100，这是错误的。
 */
@Service
public class DirtyRead {
    @Autowired
    UserDao userDao;

    @Transactional(isolation = Isolation.READ_UNCOMMITTED)
    public void func(int i) throws InterruptedException {
        System.out.println("事务：" + i + " Start");
        if(i==2){
            // 等待事务1更新完再读取
            Thread.sleep(5000);
        }
        String pw = userDao.selectByName("renchao").getValue();
        System.out.println("事务：" + i + " 当前Value：" + pw);
        boolean status = userDao.update("renchao", String.valueOf(Integer.valueOf(pw) + 50));
        System.out.println("事务：" + i + " 更新后Value：" + userDao.selectByName("renchao").getValue());

        Thread.sleep(8000);
        if(i==1){
            System.out.println("事务："+i +"进行回滚");
            // 事务1进行回滚操作
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
        }
//        System.out.println("事务+"+i+"  更新后密码："+userDao.selectByName("renchao").getValue());
    }
}