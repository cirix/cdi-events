package com.dummy.cdi.events.beans.services.events.consumers;

import com.dummy.cdi.events.response.services.DummyServiceCallbackBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ejb.Stateless;
import javax.enterprise.event.Observes;
import java.io.Serializable;

@Stateless(name="dummyServiceConsumerBean")
public class DummyServiceEventConsumerBean implements Serializable{
    private static final long serialVersionUID = 4792937206691642073L;
    private static final Logger logger = LoggerFactory.getLogger(DummyServiceCallbackBean.class);


    public void onCallBackEvent(@Observes String event){
        logger.info(event);
    }
}
