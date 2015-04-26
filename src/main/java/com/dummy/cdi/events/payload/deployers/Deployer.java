package com.dummy.cdi.events.payload.deployers;

import com.dummy.cdi.events.Service;

import java.io.Serializable;
import java.util.Properties;

public class Deployer implements Serializable{
    private static final long serialVersionUID = 31661047058209509L;

    private String jsonObject = "{value:\"My Service Register\"}";
    private Properties properties;

    public String getJsonObject() {
        return jsonObject;
    }

    public void setJsonObject(String jsonObject) {
        this.jsonObject = jsonObject;
    }

    public Properties getProperties() {
        return properties;
    }

    public void setProperties(Properties properties) {
        this.properties = properties;
    }


    public String toString(){
        return jsonObject;
    }
}
