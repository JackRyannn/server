package com.renchao.server.mysqltest;

import com.renchao.server.dao.UserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;


/**
 * 第一类更新丢失问题
 * 因为回滚导致其他更新丢失，所有的事务隔离级别都可以预防。
 * 举例：事务1取值value（初始值为0，我这里用Value代替），然后更新为value+50，暂停两秒，进行回滚。
 * 事务2取值value，此时事务1还未回滚，更新为value+100；两秒后事务1回滚，但并未回到初始值，而是成功+100。
 * 这里用了注解式事务。
 */
@Service
public class UpdateLostFirst {
    @Autowired
    UserDao userDao;

    @Transactional(isolation = Isolation.READ_UNCOMMITTED)
    public void func(int i) throws InterruptedException {
        System.out.println("事务：" + i + " Start");
        String pw = userDao.selectByName("renchao").getValue();
        System.out.println("事务：" + i + " 当前Value：" + pw);
        boolean status = userDao.update("renchao", String.valueOf(Integer.valueOf(pw) + 50 * i));
        Thread.sleep(2000);
        if(i==1){
            System.out.println("事务："+i +"进行回滚");
            // 回滚操作
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
        }
//        System.out.println("事务+"+i+"  更新后密码："+userDao.selectByName("renchao").getValue());
    }
}

