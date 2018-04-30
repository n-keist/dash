package io.lemony.dash.service.account;

import io.lemony.dash.Bootstrap;
import spark.Request;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Session {

    private Request request;
    private String token;
    private Account account;
    private int userId;

    public Session(Request request) {
        this.request = request;
        if(this.cookieAvailable()) {
            this.token = this.getRequest().cookie("LINF");
            inSession();
        }
    }

    public Request getRequest() {
        return this.request;
    }

    public String getToken() {
        return this.token;
    }

    public Account getAccount() {
        return this.account;
    }

    public int getUserId() {
        return this.userId;
    }

    public boolean cookieAvailable() {
        return this.request.cookies().containsKey("LINF")
                && !this.request.cookies().get("LINF").equalsIgnoreCase("");
    }

    public boolean inSession() {
        if(this.cookieAvailable()) {
            this.token = this.request.cookies().get("LINF");
            ResultSet resultSet = null;
            try {
                PreparedStatement preparedStatement = Bootstrap.sqlConnection.getConnection().prepareStatement(
                        "SELECT `user` FROM `login` WHERE `token` = ?;"
                );
                preparedStatement.setString(1, this.token);
                resultSet = preparedStatement.executeQuery();
                if(resultSet.next()) {
                    this.account = new Account(this);
                    this.userId = resultSet.getInt("user");
                    return true;
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
        return false;
    }

    @Override
    public String toString() {
        String out = "toString() @ Session \n";
                out += "token: '" + this.token + "'\n";
                out += "userId: '" + this.userId + "'\n";
                out += "cookieAvailable(): '" + this.cookieAvailable() + "'\n";
                out += "inSession(): '" + this.inSession() + "'";
                out += "finished";
        return out;
    }
}
