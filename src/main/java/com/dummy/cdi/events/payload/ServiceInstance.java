package com.dummy.cdi.events.payload;

import com.dummy.cdi.events.Service;

public class ServiceInstance implements Service{
    private static final long serialVersionUID = 3596287247449216948L;

    private String description,version,endpoint;
    private byte[] data;

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String getEndpoint() {
        return endpoint;
    }

    public void setEndpoint(String endpoint) {
        this.endpoint = endpoint;
    }
    public void setVersion(String version) {
        this.version = version;
    }

    public void setData(byte[] data) {
        this.data = data;
    }

    public String getDescription() {
        return this.description;
    }

    public String getVersion() {
        return this.version;
    }

    public byte[] getData() {
        return data;
    }


}
