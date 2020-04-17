package com.xlx.majiang;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@MapperScan("com.xlx.majiang.system.dao")
@EnableScheduling
@EnableSwagger2
public class MajiangApplication {
    
    public static void main(String[] args) {
        // SpringApplication springApplication = new SpringApplication(MajiangApplication.class);
        // 关闭banner
        // springApplication.setBannerMode(Banner.Mode.OFF);
        // springApplication.run(args);
        SpringApplication.run(MajiangApplication.class, args);
    }
    
}
