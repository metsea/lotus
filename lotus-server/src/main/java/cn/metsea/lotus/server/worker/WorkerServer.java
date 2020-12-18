package cn.metsea.lotus.server.worker;

import cn.metsea.lotus.server.master.MasterServer;
import cn.metsea.lotus.server.worker.registry.WorkerRegistry;
import javax.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;

/**
 * Worker Server
 */
@Slf4j
@ComponentScan(
    basePackages = "cn.metsea.lotus",
    excludeFilters = {
        @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = {MasterServer.class})
    }
)
public class WorkerServer {

    @Autowired
    private WorkerRegistry workerRegistry;

    public static void main(String[] args) {
        Thread.currentThread().setName("WorkerServer");
        new SpringApplicationBuilder(WorkerServer.class).web(WebApplicationType.NONE).run(args);
    }

    @PostConstruct
    public void run() throws InterruptedException {
        // registry
        log.info("start worker server ...");
        this.workerRegistry.registry();

        Thread.sleep(1000 * 600);
    }

}
