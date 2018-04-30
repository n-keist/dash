package io.lemony.dash.ws;

import com.google.gson.JsonObject;

public abstract class WSCommand {

    private String endpoint;

    public WSCommand(String endpoint) {
        this.endpoint = endpoint;
    }

    public String getEndpoint() {
        return endpoint;
    }

    public abstract JsonObject handle(JsonObject input);
}
