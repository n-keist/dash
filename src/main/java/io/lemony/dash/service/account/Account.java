package io.lemony.dash.service.account;

import io.lemony.dash.Bootstrap;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

public class Account {

    private int id;
    private Session session;
    private String username;
    private String passwordHashed;
    private boolean admin;
    private Timestamp timestamp;

    public Account(Session session) {
        this.session = session;
        this.id = this.session.getUserId();
        getAccountData();
    }

    private void getAccountData() {
        ResultSet resultSet = null;
        try {
            PreparedStatement preparedStatement = Bootstrap.sqlConnection.getConnection().prepareStatement(
                    "SELECT * FROM `user` WHERE `id` = ?;"
            );
            preparedStatement.setInt(1, this.id);
            resultSet = preparedStatement.executeQuery();
            if(resultSet.next()) {
                this.username = resultSet.getString("username");
                this.passwordHashed = resultSet.getString("password");
                this.admin = resultSet.getInt("administrator") == 1;
                this.timestamp = resultSet.getTimestamp("timestamp");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                resultSet.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public int getId() {
        return id;
    }

    public Session getSession() {
        return session;
    }

    public String getPasswordHashed() {
        return passwordHashed;
    }

    public String getUsername() {
        return username;
    }

    public Timestamp getTimestamp() {
        return timestamp;
    }

    public boolean isAdmin() {
        return admin;
    }
}
