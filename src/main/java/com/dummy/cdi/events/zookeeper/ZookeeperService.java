package com.dummy.cdi.events.zookeeper;


import com.dummy.cdi.events.Service;
import org.apache.zookeeper.*;
import org.apache.zookeeper.data.ACL;
import org.apache.zookeeper.data.Stat;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import javax.ejb.Singleton;
import javax.ejb.Stateless;
import javax.inject.Inject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Singleton
public class ZookeeperService implements Serializable{
    private static final long serialVersionUID = 3607178392179991956L;
    private static final Logger logger = LoggerFactory.getLogger(ZookeeperService.class);

    private ZooKeeper zooKeeper;

    @Inject
    private ZookeeperConnectionFactory zookeeperConnectionFactory;


    /**
     * A method for retrieving the masters registered for a path in a zookeeper ensemble
     * @param path : The path registered.
     * @return A <b>List<String></b> that contains strings of format ip:port
     */
    public List<String> getMasterEnsemble(String path){
        zooKeeper = zookeeperConnectionFactory.getConnection();
        List<String> masterEnsemble = new ArrayList<String>();
        try {
            List<String> children  = zooKeeper.getChildren(path,false);
            for (String child : children) {
                String readData = new String(zooKeeper.getData(path+"/"+child,false,new Stat()));
                logger.info("Data read:{}",readData);
                if(readData.contains("master@")){
                    masterEnsemble.add(readData.substring(readData.indexOf("@")+1,readData.indexOf("*")));
                }
            }
        } catch (KeeperException | InterruptedException e) {
            logger.error("Error error:",e.getCause());
        } finally {
            logger.info("[Action]->Closing the connection to zookeeper.");
            try {
                zooKeeper.close();
            } catch (InterruptedException e) {
                logger.error("Error:",e.getCause());
            }
        }
        return  masterEnsemble;
    }

    /**
     * Method for registering for a path.
     * @param path: The path of interest.
     * @return : a <b>boolean</b> indicating the success of the failure of the process
     */
    public boolean registerService(String path,byte[] nodeData){
        boolean succeed = false;
        try{
            zooKeeper = zookeeperConnectionFactory.getConnection();
            String messageCreate= zooKeeper.create(path, null, ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
            logger.info("Registering result:{}",messageCreate);
            succeed=true;
        } catch (KeeperException | InterruptedException e) {
            logger.error("Error:{}",e);
        }finally{
            try{
                zooKeeper.close();
            }catch(InterruptedException e){
                logger.error("Error:",e.getCause());
            }
        }
        return succeed;
    }


}
