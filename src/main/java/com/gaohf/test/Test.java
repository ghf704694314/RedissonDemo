package com.gaohf.test;

import com.gaohf.utils.HttpUtils;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * com.itmuch.cloud.study.consumer.utils
 *
 * @Author : Gaohf
 * @Description :
 * @Date : 2018/1/26
 */
public class Test {

    private int corePoolSize=15;
    private int maximumPoolSize=20;
    private long keepAliveTime=30;
    private TimeUnit timeUnit=TimeUnit.SECONDS;
    private BlockingQueue<Runnable> workQueue=new ArrayBlockingQueue<Runnable>(5);

    private static ThreadPoolExecutor  executorService;

    public ThreadPoolExecutor  executorService(){
        this.executorService=new ThreadPoolExecutor(corePoolSize,maximumPoolSize,keepAliveTime,timeUnit,workQueue);
        return executorService;
    }

    public static void main(String[] args) {
        Test test=new Test();
        ThreadPoolExecutor  executorService=test.executorService();
       for(int i=0;i<=10;i++){
           executorService.execute(new Runnable() {
               @Override
               public void run() {
                   try {
                       Thread.currentThread().sleep(4000);
                       String result= HttpUtils.post("http://localhost:9020/test/add1","{\"name\":\"ghf\",\"phone\":\"15058221097\"}");
                       System.out.println(result);
                   }catch (InterruptedException e){
                       System.out.println(e.getMessage());
                   }
               }
           });
           System.out.println("线程池中线程数目："+executorService.getPoolSize()+"，队列中等待执行的任务数目："+
                   executorService.getQueue().size()+"，已执行玩别的任务数目："+executorService.getCompletedTaskCount());
       }
        executorService.shutdown();
    }
}
