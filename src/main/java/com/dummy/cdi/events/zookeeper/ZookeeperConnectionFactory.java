package com.dummy.cdi.events.zookeeper;

import com.dummy.cdi.events.zookeeper.watcher.CdiWatcher;
import org.apache.zookeeper.ZooKeeper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import javax.ejb.Singleton;
import javax.enterprise.context.SessionScoped;
import java.io.IOException;
import java.io.Serializable;
import java.util.concurrent.TimeUnit;

@Singleton
public class ZookeeperConnectionFactory implements Serializable{
    private static final Logger logger = LoggerFactory.getLogger(ZookeeperConnectionFactory.class);
    private static final long serialVersionUID = -5349403327084706445L;


    private String connectionString = "192.168.59.107:2181,192.168.59.108:2181,192.168.59.104:2181";

    private int sessionTimeout = 15000;

    private ZooKeeper zooKeeper = null;

    public ZookeeperConnectionFactory(){
        super();
    }

    public ZooKeeper getConnection(){
        try {
            zooKeeper = new ZooKeeper(connectionString,sessionTimeout,new CdiWatcher());
            Thread.sleep(5000);
        } catch (IOException | InterruptedException e) {
            logger.error("Error:",e);
        }
        return zooKeeper;
    }
}
