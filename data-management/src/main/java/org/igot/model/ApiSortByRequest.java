package org.igot.model;

import org.igot.utils.Constants;

public class ApiSortByRequest {

    private String channel = Constants.ASC_ORDER;

    public String getChannel() {
        return channel;
    }

    public void setChannel(String channel) {
        this.channel = channel;
    }
}
