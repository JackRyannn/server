package com.renchao.server.mysqltest;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UnRepeatableReadTest {
    @Autowired
    UnRepeatableRead unRepeatableRead;

    @Test
    public void test() throws InterruptedException {
        new Thread(() -> {
            try {
                unRepeatableRead.func(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();
        unRepeatableRead.func(2);
        System.out.println("事务2已提交");
        Thread.sleep(5000);
    }
}