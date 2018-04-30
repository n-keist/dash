package io.lemony.dash.ws.commands;

import com.google.gson.JsonObject;
import io.lemony.dash.Utils;
import io.lemony.dash.ws.WSCommand;

public class PingCommand extends WSCommand {

    public PingCommand() {
        super("/ping");
    }

    @Override
    public JsonObject handle(JsonObject input) {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("response-code", 200);
        jsonObject.addProperty("response-bool", true);
        JsonObject responseObject = new JsonObject();
        responseObject.addProperty("message", "Pong!");
        jsonObject.add("response", responseObject);
        return jsonObject;
    }
}
