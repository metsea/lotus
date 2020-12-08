package cn.metsea.lotus.api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;

/**
 * api server
 */
@SpringBootApplication
@ComponentScan(
    basePackages = "cn.metsea.lotus",
    excludeFilters = @ComponentScan.Filter(type = FilterType.REGEX, pattern = "cn.metsea.lotus.server.*")
)
public class ApiServer {

    public static void main(String[] args) {
        Thread.currentThread().setName("ApiServer");
        SpringApplication.run(ApiServer.class, args);
    }

}
