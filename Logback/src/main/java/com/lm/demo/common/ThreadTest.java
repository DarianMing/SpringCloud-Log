package com.lm.demo.common;

import org.slf4j.MDC;

/**
 * @ClassName ThreadTest
 * @Description https://blog.csdn.net/sunzhenhua0608/article/details/29175283
 * @Author Admin
 * @DATE 2018/12/25 10:19
 **/
public class ThreadTest extends Thread {
    private int i;
    public ThreadTest(){

    }

    public ThreadTest(int i){
        this.i = i;
    }

    @Override
    public void run() {
        System.out.println(++i);
        MDC.put("username" , i + "");
        for (int j = 0; j < 100 ; j++) {
            System.out.println("aaa" + i + " " + j);
            if (j == 10) {
                try {
                    this.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
        System.out.println("run: " + i + "   " + MDC.get("username"));
    }

    public static void main(String[] args) {
        ThreadTest t1 = new ThreadTest(1);
        t1.start();
        ThreadTest t2 = new ThreadTest(2);
        t2.start();
    }
}
