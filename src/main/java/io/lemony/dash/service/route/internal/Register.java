package io.lemony.dash.service.route.internal;

import io.lemony.dash.Bootstrap;
import org.mindrot.jbcrypt.BCrypt;
import spark.Request;
import spark.Response;
import spark.Route;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Register implements Route {
    @Override
    public Object handle(Request request, Response response) throws SQLException {
        String username = request.queryParams("username");
        String password = request.queryParams("password");
        String password_2 = request.queryParams("password_2");

        if(password.equals(password_2)) {
            PreparedStatement preparedStatement = Bootstrap.sqlConnection.getConnection().prepareStatement("SELECT `id` FROM `user` WHERE `username` = ?");
            preparedStatement.setString(1, username);
            ResultSet resultSet = preparedStatement.executeQuery();

            if(!resultSet.next()) {
                PreparedStatement insertStatement = Bootstrap.sqlConnection.getConnection().prepareStatement("INSERT INTO `user` VALUES (NULL, ?, ?, 0, NULL);");
                insertStatement.setString(1, username);
                insertStatement.setString(2, BCrypt.hashpw(password, BCrypt.gensalt()));
                insertStatement.executeUpdate();
                return "{\"type\": \"success\", \"message\": \"Account created.\"}";
            } else {
                return "{\"type\": \"warning\", \"message\": \"A user with this account name already exists.\"}";
            }
        } else {
            return "{\"type\": \"warning\", \"message\": \"Your passwords don't match\"}";
        }
    }
}
