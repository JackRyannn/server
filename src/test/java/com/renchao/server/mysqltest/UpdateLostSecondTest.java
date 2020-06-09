package com.renchao.server.mysqltest;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UpdateLostSecondTest {
    @Autowired
    UpdateLostSecond updateLostSecond;
    @Test
    public void test() throws InterruptedException {
        new Thread(() -> {
            try {
                updateLostSecond.func(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();
        updateLostSecond.func(2);

    }
}