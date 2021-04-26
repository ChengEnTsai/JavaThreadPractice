package org.example;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * @author Ken
 * @date 2021/04/26
 * @project JavaThreadPractice
 */
@SpringBootApplication
@EnableScheduling
public class JavaThreadPracticeApplication {

    public static void main(String[] args) {
        SpringApplication.run(JavaThreadPracticeApplication.class, args);
    }

}
