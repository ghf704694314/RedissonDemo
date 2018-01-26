package com.gaohf.service;

import com.gaohf.entity.TestLock;
import com.gaohf.repository.TestLockRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


/**
 * com.itmuch.cloud.study.service
 *
 * @Author : Gaohf
 * @Description :
 * @Date : 2018/1/26
 */
@Service
public class TestLockService {

    @Autowired
    private TestLockRepository testLockRepository;

    public TestLock saveOrUpdate(TestLock testLock){
        return testLockRepository.save(testLock);
    }

    public List<TestLock> findByPhone(String phone) {
        return testLockRepository.findByPhone(phone);
    }
}
