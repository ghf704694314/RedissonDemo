package com.gaohf.controller;

import com.gaohf.entity.TestLock;
import com.gaohf.request.ReqTestLock;
import com.gaohf.service.TestLockService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * com.itmuch.cloud.study.provider
 *
 * @Author : Gaohf
 * @Description :
 * @Date : 2018/1/26
 */
@RestController
@Slf4j
public class TestUnLockAddController {

    @Autowired
    private TestLockService testLockService;

    @RequestMapping(value = "/test/add2",method = RequestMethod.POST)
    public String add(@RequestBody ReqTestLock reqTestLock) {
        TestLock testLock;
        try {
            List<TestLock> testLockList= testLockService.findByPhone(reqTestLock.getPhone());
            if (testLockList != null&&testLockList.size()>0) {
                log.info("号码重复,号码{}",reqTestLock.getPhone());
                return "号码"+reqTestLock.getPhone()+"重复";
            }
            testLock = new TestLock();
            testLock.setName(reqTestLock.getName());
            testLock.setPhone(reqTestLock.getPhone());
            //只在保存到数据库时获取锁,如果有其他业务逻辑需要加在这下面
            testLockService.saveOrUpdate(testLock);
            return "SUCCESS";
        } catch (Exception e) {
            log.info("{}",e.getMessage());
            return "FAIL,错误原因"+e.getMessage();
        }
    }

}
