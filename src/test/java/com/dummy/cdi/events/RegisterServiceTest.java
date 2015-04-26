package com.dummy.cdi.events;

import com.dummy.cdi.events.zookeeper.ZookeeperService;
import junit.framework.Assert;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.Test;
import org.junit.runner.RunWith;

import javax.inject.Inject;

/**
 * Created by nmpallas on 25/04/15.
 */
@RunWith(Arquillian.class)
public class RegisterServiceTest {


    @Test
    public void registerServiceTest(){
        String path = "/my/node/path";

    }
}
