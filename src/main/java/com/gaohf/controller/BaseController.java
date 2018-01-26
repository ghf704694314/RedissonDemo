package com.gaohf.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * com.itmuch.cloud.study.provider
 *
 * @Author : Gaohf
 * @Description :
 * @Date : 2018/1/26
 */
@RestController
public class BaseController {

    @RequestMapping(value = "/hello")
    public Object hello(){
        return "Hello World!";
    }
}
