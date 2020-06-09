package com.renchao.server.tool;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CountDownLatch;

public class concurrentTool {
    static final int BINGFASHU = 100;
    static Map params = new HashMap();
    static CountDownLatch latch = new CountDownLatch(BINGFASHU);

    static class MyRunnable implements Runnable {

        @Override
        public void run() {
            try {
                latch.await();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
//            并发操作
            System.out.println(System.currentTimeMillis());
            //params.put("phoneNo", "中文");
            String str = HttpTool.get("http://localhost:8081/blog/35", params, 10000, 10000, "UTF-8");
            System.out.println(str);
        }
    }

    public static void main(String[] args) {
        for (int i = 0; i < BINGFASHU; i++) {
            new Thread(new MyRunnable()).start();
            latch.countDown();
        }

    }
}
