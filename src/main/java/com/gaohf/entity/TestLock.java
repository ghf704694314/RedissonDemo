package com.gaohf.entity;

import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.util.Date;

/**
 * com.itmuch.cloud.study.entity
 *
 * @Author : Gaohf
 * @Description :
 * @Date : 2018/1/26
 */
@Entity
@Table(name = "t_test_lock")
@Data
@EntityListeners(AuditingEntityListener.class)
public class TestLock {

    @Id
    @GeneratedValue
    private Long id;
    private String name;
    private String phone;

    @CreatedDate
    private Date createTime;
    @LastModifiedDate
    private Date updateTime;
}
