package io.lemony.dash;

import io.lemony.dash.json.SqlCredentials;
import io.lemony.dash.service.ShutdownHook;
import io.lemony.dash.service.SqlConfiguration;
import io.lemony.dash.service.SqlConnection;
import io.lemony.dash.service.WebService;

public class Bootstrap {

    public static SqlConnection sqlConnection;

    public static void main(String[] args) {
        SqlCredentials sqlCredentials = new SqlConfiguration().getCredentials();
        sqlConnection = new SqlConnection(sqlCredentials).connect();
        sqlConnection.start();

        Runtime.getRuntime().addShutdownHook(new ShutdownHook());

        WebService.ignite();
    }

}
