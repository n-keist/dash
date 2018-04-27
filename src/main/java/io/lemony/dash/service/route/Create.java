package io.lemony.dash.service.route;

import spark.ModelAndView;
import spark.Request;
import spark.Response;
import spark.Route;
import spark.template.velocity.VelocityTemplateEngine;

import java.util.HashMap;

public class Create implements Route {
    @Override
    public Object handle(Request request, Response response) throws Exception {
        return new VelocityTemplateEngine().render(new ModelAndView(new HashMap<>(), "ref/create.vm"));
    }
}
