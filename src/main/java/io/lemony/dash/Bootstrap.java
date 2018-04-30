package io.lemony.dash;

import io.lemony.dash.json.SqlCredentials;
import io.lemony.dash.service.ShutdownHook;
import io.lemony.dash.service.SqlConfiguration;
import io.lemony.dash.service.SqlConnection;
import io.lemony.dash.service.WebService;
import io.lemony.dash.ws.WSCommand;
import org.reflections.Reflections;

public class Bootstrap {

    public static SqlConnection sqlConnection;

    public static void main(String[] args) {
        SqlCredentials sqlCredentials = new SqlConfiguration().getCredentials();
        sqlConnection = new SqlConnection(sqlCredentials).connect();
        sqlConnection.start();

        Runtime.getRuntime().addShutdownHook(new ShutdownHook());

        Reflections reflections = new Reflections("io.lemony.dash.ws.commands");
        reflections.getSubTypesOf(WSCommand.class).forEach(wsCommand -> {
            try {
                WSCommand command = wsCommand.newInstance();
                Utils.ws_commandmap.put(command.getEndpoint(), command);
            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        });

        WebService.ignite();
    }

}
