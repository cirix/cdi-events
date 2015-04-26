package com.dummy.cdi.events.beans;

import com.dummy.cdi.events.payload.ServiceInstance;
import com.dummy.cdi.events.payload.deployers.Deployer;
import com.dummy.cdi.events.zookeeper.ZookeeperService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ejb.Stateless;
import javax.enterprise.event.Observes;
import javax.inject.Inject;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.Serializable;

@Stateless(name="eventConsumer")
public class EventConsumerBean implements Serializable{
    private static final long serialVersionUID = 4775947387215201659L;
    private static final Logger logger = LoggerFactory.getLogger(EventConsumerBean.class);

    @Inject
    private ZookeeperService zookeeperService;



    /**
     * The observer method for registration requests.
     * @param serviceInstance : The instance data to save in Zookeeper.
     */
    public void onRegisterEvent(@Observes ServiceInstance serviceInstance) {
        try {
            ObjectInputStream objectInputStream = new ObjectInputStream(new ByteArrayInputStream(serviceInstance.getData()));
            Deployer deployer = (Deployer)objectInputStream.readObject();
            logger.info("Object read:{}",deployer.toString());
            zookeeperService.getMasterEnsemble(serviceInstance.getEndpoint());
        } catch (IOException | ClassNotFoundException e) {
            logger.error("Failed to do stuff:{}",e);
        }
    }
}
