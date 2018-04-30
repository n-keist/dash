package io.lemony.dash.service;

import io.lemony.dash.Bootstrap;
import io.lemony.dash.service.route.Admin;
import io.lemony.dash.service.route.Create;
import io.lemony.dash.service.route.Login;
import io.lemony.dash.service.route.Root;
import io.lemony.dash.service.route.internal.Register;
import io.lemony.dash.service.route.websocket.Stream;
import spark.Spark;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

import static spark.Spark.*;

public class WebService {

    private static WebService instance;

    private WebService() {
        Spark.port(80);

        webSocket("/stream", Stream.class);

        staticFiles.location("/res/");

        initExceptionHandler((e) -> e.printStackTrace());

        before((request, response) -> {
          response.header("Server", "d a s h / 0.1-DEV");
          if(request.pathInfo().isEmpty() || request.pathInfo().equals("/")) {
              if(request.cookies().containsKey("LINF")) {
                  String token = request.cookie("LINF");
                  PreparedStatement preparedStatement = Bootstrap.sqlConnection.getConnection().prepareStatement("SELECT `id` FROM `login` WHERE `token` = ?;");
                  preparedStatement.setString(1, token);
                  ResultSet resultSet = preparedStatement.executeQuery();
                  if(resultSet.next()) {
                      response.redirect("/app/");
                  } else {
                      response.redirect("/app/login");
                  }
              } else {
                  response.redirect("/app/login");
              }
          }
        });

        path("/app", () -> {
            before((request, response) -> {
                String token = request.cookie("LINF");
                PreparedStatement preparedStatement = Bootstrap.sqlConnection.getConnection().prepareStatement("SELECT `id` FROM `login` WHERE `token` = ?;");
                preparedStatement.setString(1, token);
                ResultSet resultSet = preparedStatement.executeQuery();
                if(!resultSet.next()) {
                    response.redirect("/app/login");
                }
            });

            get("/", new Root());
            get("/login", new Login());
            get("/create", new Create());
            get("/admin", new Admin());
        });

        path("/internal", () -> path("/post", WebService::addRoutes));

    }

    public static WebService ignite() {
        if(instance == null) {
            instance = new WebService();
        }
        return instance;
    }

    private static void addRoutes() {
        post("/login", new io.lemony.dash.service.route.internal.Login());
        post("/register", new Register());
    }
}
