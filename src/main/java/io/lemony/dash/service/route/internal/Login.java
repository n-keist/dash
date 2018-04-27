package io.lemony.dash.service.route.internal;

import io.lemony.dash.Bootstrap;
import io.lemony.dash.Utils;
import org.mindrot.jbcrypt.BCrypt;
import spark.Request;
import spark.Response;
import spark.Route;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class Login implements Route {

    @Override
    public Object handle(Request request, Response response) throws Exception {
        String username = request.queryParams("username");
        String password = request.queryParams("password");

        PreparedStatement preparedStatement = Bootstrap.sqlConnection.getConnection().prepareStatement("SELECT `password`,`id` FROM `user` WHERE `username` = ?");
        preparedStatement.setString(1, username);
        ResultSet resultSet = preparedStatement.executeQuery();

        if(resultSet.next()) {
            if(BCrypt.checkpw(password, resultSet.getString("password"))) {
                String token = Utils.randomString(32);
                //response.cookie("LINF", token);
                response.cookie("/", "LINF", token, (int)(System.currentTimeMillis() / 1000) +60 * 60 * 24 * 30, false);
                int userId = resultSet.getInt("id");
                PreparedStatement insertStatement = Bootstrap.sqlConnection.getConnection().prepareStatement("INSERT INTO `login` VALUES (NULL, ?, ?, ?, NULL);");
                insertStatement.setInt(1, userId);
                insertStatement.setString(2, token);
                insertStatement.setString(3, request.userAgent());
                insertStatement.executeUpdate();
                response.redirect("/app/");
            } else {
                return "{\"type\": \"warning\", \"message\": \"Incorrect Password\"}";
            }
        } else {
            return "{\"type\": \"warning\", \"message\": \"Account does not exist.\"}";
        }

        return null;
    }
}
