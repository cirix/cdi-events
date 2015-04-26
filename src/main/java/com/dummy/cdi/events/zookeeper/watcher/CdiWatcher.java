package com.dummy.cdi.events.zookeeper.watcher;

import org.apache.zookeeper.AsyncCallback;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.data.Stat;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CdiWatcher implements Watcher{
    private static final Logger logger = LoggerFactory.getLogger(CdiWatcher.class);

    @Override
    public void process(WatchedEvent event) {
        if(event.getType().getIntValue() == 100){
            logger.info("Read value of event type:{}",event.getType().getIntValue());
        }
    }

    AsyncCallback.DataCallback dataCallback = new AsyncCallback.DataCallback() {
        @Override
        public void processResult(int rc, String path, Object ctx, byte[] data, Stat stat) {
            switch(KeeperException.Code.get(rc)){
                case CONNECTIONLOSS:{

                }
            }
        }
    };

}
