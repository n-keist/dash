package io.lemony.dash.service.route;

import io.lemony.dash.Bootstrap;
import io.lemony.dash.json.Alert;
import io.lemony.dash.service.account.Account;
import io.lemony.dash.service.account.Session;
import spark.ModelAndView;
import spark.Request;
import spark.Response;
import spark.Route;
import spark.template.velocity.VelocityTemplateEngine;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;

public class Root implements Route {

    @Override
    public Object handle(Request request, Response response) throws SQLException {
        HashMap<String, Object> model = new HashMap<>();

        if(!request.cookies().containsKey("LINF")) {
            Alert errorNotice = new Alert();
            errorNotice.type = "warning";
            errorNotice.message = "Authentication failed";
            model.put("failed", errorNotice);
        }

        model.put("errorAvailable", model.containsKey("failed"));
        Session session = new Session(request);
        if(session.inSession()) {
            Account account = session.getAccount();
            model.put("userName", account.getUsername());
            model.put("userId", account.getId());
        }

        return new VelocityTemplateEngine().render(new ModelAndView(model, "ref/index.vm"));
    }

}
