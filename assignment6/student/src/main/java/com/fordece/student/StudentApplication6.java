package com.fordece.student;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


// mybatis 还未来得及支持springboot3.2
//https://github.com/mybatis/spring/issues/855
@SpringBootApplication
public class StudentApplication6 {

    public static void main(String[] args) {
        SpringApplication.run(StudentApplication6.class, args);
    }

}
