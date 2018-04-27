package io.lemony.dash.service;

import io.lemony.dash.Bootstrap;

import java.sql.SQLException;

public class ShutdownHook extends Thread {

    @Override
    public void run() {
        Bootstrap.sqlConnection.interrupt();
        try {
            Bootstrap.sqlConnection.getConnection().close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
