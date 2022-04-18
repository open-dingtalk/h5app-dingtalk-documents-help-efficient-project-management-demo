package com.dingtalk;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

/**
 * @author qwh.shuwu
 * @date 2022/3/16 2:49 下午
 */
@ComponentScan(basePackages = {"com.aliyun.dingboot.common","com.dingtalk"})
@SpringBootApplication
public class Application {
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
