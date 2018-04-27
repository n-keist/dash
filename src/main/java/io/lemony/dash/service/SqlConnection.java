package io.lemony.dash.service;

import io.lemony.dash.json.SqlCredentials;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class SqlConnection extends Thread {

    private SqlCredentials credentials;
    private Connection connection;
    private boolean STATE_CONNECTING = false;

    public SqlConnection(SqlCredentials credentials) {
        this.credentials = credentials;
    }

    public SqlConnection connect() {
        try {
            this.connection = DriverManager.getConnection("jdbc:mysql://" + this.credentials.hostname + ":" + this.credentials.port + "/" + this.credentials.database + "?autoReconnect=true", this.credentials.username, this.credentials.password);
            System.out.println("[D A S H] A connection was opened successfully.");
            if(this.credentials.tables != null) {
                for (String command : this.credentials.tables) {
                    Statement statement = this.connection.createStatement();
                    statement.executeUpdate(command);
                    statement.close();
                }
            }
        } catch (SQLException ignore) { }
        return this;
    }

    public Connection getConnection() {
        return connection;
    }

    @Override
    public void run() {
        try {
            while((this.connection == null) || (this.connection.isClosed() && !this.STATE_CONNECTING)) {
                this.STATE_CONNECTING = true;
                this.connection = DriverManager.getConnection("jdbc:mysql://" + this.credentials.hostname + ":" + this.credentials.port + "/" + this.credentials.database + "?autoReconnect=true", this.credentials.username, this.credentials.password);
                this.STATE_CONNECTING = false;
                System.out.println("[D A S H] The Sql-connection was re-opened.");
            }
        } catch (Exception e) {
            System.out.println("[D A S H] Failed re-opening the connection.");
            e.printStackTrace();
        }
    }
}
