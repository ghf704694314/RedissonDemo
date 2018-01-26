package com.gaohf.repository;

import com.gaohf.entity.TestLock;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

/**
 * com.itmuch.cloud.study.repository
 *
 * @Author : Gaohf
 * @Description :
 * @Date : 2018/1/26
 */
public interface TestLockRepository extends JpaSpecificationExecutor<TestLock>,JpaRepository<TestLock,Long> {
    List<TestLock> findByPhone(String phone);
}
