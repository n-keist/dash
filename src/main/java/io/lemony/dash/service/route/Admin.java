package io.lemony.dash.service.route;

import io.lemony.dash.service.account.Account;
import io.lemony.dash.service.account.Session;
import spark.ModelAndView;
import spark.Request;
import spark.Response;
import spark.Route;
import spark.template.velocity.VelocityTemplateEngine;

import java.util.HashMap;

public class Admin implements Route {

    @Override
    public Object handle(Request request, Response response) throws Exception {
        HashMap<String, Object> model = new HashMap<>();

        Session session = new Session(request);
        if(session.inSession()) {
            Account account = session.getAccount();
            model.put("userName", account.getUsername());
            model.put("userId", account.getId());
        }

        return new VelocityTemplateEngine().render(new ModelAndView(model, "ref/admin.vm"));
    }

}
