package com.web.platform;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.fusesource.jansi.AnsiConsole;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


/**
 * @author YaeMiko
 */
@SpringBootApplication
@MapperScan("com.web.platform.mapper")
public class HouseLeasePlatformApplication {

    private static final Logger logger = LogManager.getLogger(HouseLeasePlatformApplication.class);

    public static void main(String[] args) {

        AnsiConsole.systemInstall();

        SpringApplication.run(HouseLeasePlatformApplication.class, args);

    }
}



