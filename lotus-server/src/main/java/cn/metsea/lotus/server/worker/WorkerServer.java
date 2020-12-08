package cn.metsea.lotus.server.worker;

import cn.metsea.lotus.server.master.MasterServer;
import javax.annotation.PostConstruct;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;

/**
 * worker server
 */
@ComponentScan(
    basePackages = "cn.metsea.lotus",
    excludeFilters = {
        @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = {MasterServer.class})
    }
)
public class WorkerServer {

    public static void main(String[] args) {
        Thread.currentThread().setName("WorkerServer");
        new SpringApplicationBuilder(WorkerServer.class).web(WebApplicationType.NONE).run(args);
    }

    @PostConstruct
    public void run() {
        // TDO
    }

}
