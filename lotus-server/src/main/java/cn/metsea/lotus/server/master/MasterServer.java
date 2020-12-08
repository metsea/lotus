package cn.metsea.lotus.server.master;

import cn.metsea.lotus.server.worker.WorkerServer;
import javax.annotation.PostConstruct;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;

/**
 * master server
 */
@ComponentScan(
    basePackages = "cn.metsea.lotus",
    excludeFilters = {
        @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = {WorkerServer.class})
    }
)
public class MasterServer {

    public static void main(String[] args) {
        Thread.currentThread().setName("MasterServer");
        new SpringApplicationBuilder(MasterServer.class).web(WebApplicationType.NONE).run(args);
    }

    @PostConstruct
    public void run() {
        // TDO
    }
}
