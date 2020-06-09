package com.renchao.server.mysqltest;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UpdateLostFirstTest {
    @Autowired
    UpdateLostFirst updateLostFirst;

    @Test
    public void test() throws InterruptedException {
        new Thread(() -> {
            try {
                updateLostFirst.func(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();
        updateLostFirst.func(2);

    }
}