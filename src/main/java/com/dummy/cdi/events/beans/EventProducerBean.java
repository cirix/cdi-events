package com.dummy.cdi.events.beans;

import com.dummy.cdi.events.payload.ServiceInstance;
import com.dummy.cdi.events.payload.deployers.Deployer;
import com.dummy.cdi.events.zookeeper.ZookeeperService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.PostConstruct;


import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.ejb.Stateless;
import javax.enterprise.event.Event;
import javax.enterprise.inject.Any;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.lang.annotation.Annotation;


@Startup
@Singleton(name="eventProducerBean")
@Path("/discover-leader/{system}")
public class EventProducerBean implements Serializable{
    private static final long serialVersionUID = 5693753599350013603L;
    private static final Logger logger = LoggerFactory.getLogger(EventProducerBean.class);

    @Inject
    private ZookeeperService zookeeperService;

    @Inject
    @Any
    Event<ServiceInstance> registerEvent;

    @PostConstruct
    private void registerEndpoint(){
        for(Annotation annotation : this.getClass().getDeclaredAnnotations()){
            if(annotation.toString().contains("Path")){
                logger.info("[Action]->Found annotation:{}",annotation.toString());
                zookeeperService.registerService("/services" + annotation.toString().substring(annotation.toString().indexOf("/"), annotation.toString().lastIndexOf(")")), null);
            }
        }
    }


    @GET
    @Produces("application/json")
    public String createRegisterEvent(@PathParam(value = "system") String system) throws ClassNotFoundException,IOException{
        logger.info("Started creating the event.");
        ServiceInstance serviceInstance = new ServiceInstance();
        serviceInstance.setDescription("Register Event");
        serviceInstance.setVersion("1.0");
        serviceInstance.setEndpoint(system.equalsIgnoreCase("marathon")?"/marathon/leader":"/mesos/app/cdi-events");
        Deployer deployer = new Deployer();
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        ObjectOutputStream outputStream = new ObjectOutputStream(byteArrayOutputStream);
        outputStream.writeObject(deployer);
        serviceInstance.setData(byteArrayOutputStream.toByteArray());
        registerEvent.fire(serviceInstance);
        logger.info("Event fired:{}",serviceInstance.toString());
        return "{action:done}";
    }
}
