package com.dummy.cdi.events;

import com.dummy.cdi.events.zookeeper.ZookeeperService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;
import java.lang.annotation.Annotation;

@ApplicationPath("/services")
public class MyApplication extends Application {
    private static final Logger LOGGER = LoggerFactory.getLogger(MyApplication.class);

    @Inject
    private ZookeeperService zookeeperService;

    @PostConstruct
    private void registerEndpoint(){
        for(Annotation annotation :this.getClass().getDeclaredAnnotations()){
            if(annotation.toString().contains("ApplicationPath")) {
                zookeeperService.registerService(annotation.toString().substring(annotation.toString().indexOf("/"), annotation.toString().lastIndexOf(")")),null);
            }
        }
    }
}
