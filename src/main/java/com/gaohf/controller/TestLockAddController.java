package com.gaohf.controller;

import com.gaohf.entity.TestLock;
import com.gaohf.request.ReqTestLock;
import com.gaohf.service.TestLockService;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * com.itmuch.cloud.study.provider
 *
 * @Author : Gaohf
 * @Description :
 * @Date : 2018/1/26
 */
@RestController
@Slf4j
public class TestLockAddController {

    @Autowired
    private TestLockService testLockService;
    @Autowired
    private RedissonClient redissonClient;

    @RequestMapping(value = "/test/add1",method = RequestMethod.POST)
    public String add(@RequestBody ReqTestLock reqTestLock){

        long timeStr=0L;
        long timeEnd=0L;
        RLock lock=null;
        boolean lockFlag=false;
        TestLock testLock;
        try {
            //针对不可重复的索引phone开启锁,锁名按照业务名称+自定义key值拼接
            lock=redissonClient.getLock("testAdd"+reqTestLock.getPhone());
            //判断是否持有线程持有锁
            if(!lock.isLocked()){
                //等待时间1秒,持有时间3秒
                lockFlag=lock.tryLock(1,3, TimeUnit.SECONDS);
                if(lockFlag){
                    timeStr=System.currentTimeMillis();
                    log.info("获取锁成功，执行业务逻辑,时间:{}",timeStr);

                    Thread.sleep(2);
                    log.info("延迟2秒");
                    List<TestLock> testLockList =testLockService.findByPhone(reqTestLock.getPhone());
                    if(testLockList!=null&&testLockList.size()>0){
                        log.info("号码重复");
                        return "号码重复";
                    }
                    testLock=new TestLock();
                    testLock.setName(reqTestLock.getName());
                    testLock.setPhone(reqTestLock.getPhone());
                    //只在保存到数据库时获取锁,如果有其他业务逻辑需要加在这下面
                    testLockService.saveOrUpdate(testLock);
                }
            }else {
                return "获取锁失败,已有其他线程持有该锁";
            }
        }catch (InterruptedException e){
            log.info("执行出错,原因{}",e.getMessage());
        }catch (Exception e){
            log.info("执行保存出错,{}",e.getMessage());
        }finally {
            if(lockFlag){
                //程序完成记得释放锁
                lock.unlock();
                timeEnd=System.currentTimeMillis();
                log.info("解锁成功,时间{}",timeEnd);
            }
        }
        long userSecond=(timeEnd-timeStr)/1000;
        log.info("保存成功:用时{}秒",userSecond);
        return "SUCCESS";
    }
}
