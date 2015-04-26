package com.dummy.cdi.events;

import java.io.Serializable;

public interface Service extends Serializable{
    String getDescription();
    String getEndpoint();
    String getVersion();
}
