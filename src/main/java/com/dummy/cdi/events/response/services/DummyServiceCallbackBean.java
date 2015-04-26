package com.dummy.cdi.events.response.services;

import com.dummy.cdi.events.zookeeper.ZookeeperService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.PostConstruct;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.ejb.Stateless;
import javax.enterprise.event.Event;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import java.io.Serializable;
import java.lang.annotation.Annotation;

@Startup
@Singleton
@Path("/dummy-service/{caller}")
public class DummyServiceCallbackBean implements Serializable{
    private static final long serialVersionUID = 5260887158400788681L;
    private static final Logger logger = LoggerFactory.getLogger(DummyServiceCallbackBean.class);

    @Inject
    Event<String> helloUserEvent;


    @Inject
    private ZookeeperService zookeeperService;


    @PostConstruct
    private void registerEndpoint(){
        for(Annotation annotation : this.getClass().getDeclaredAnnotations()){
            if(annotation.toString().contains("Path")){
                zookeeperService.registerService("/services"+annotation.toString().substring(annotation.toString().indexOf("/"), annotation.toString().lastIndexOf(")")),null);
            }
        }
    }

    @GET
    @Produces("application/json")
    public boolean invokeService(@PathParam("caller")String caller){
        boolean succesfullInvoke = false;
        logger.info("Received request to say hello to user:{}", caller);
        if(caller == null || caller.equalsIgnoreCase("")){
            throw new IllegalArgumentException("The caller can't be empty or null");
        }
        helloUserEvent.fire("Service says :Hello "+caller);
        return !succesfullInvoke;
    }
}
