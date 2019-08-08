package com.xlx.majiang;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.xlx.majiang.mapper")
//@EnableScheduling
public class MajiangApplication {

  public static void main(String[] args) {
    SpringApplication.run(MajiangApplication.class, args);
  }

}
