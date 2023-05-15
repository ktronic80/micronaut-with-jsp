package test.tomcat;

import io.micronaut.http.annotation.Get;
import io.micronaut.views.ModelAndView;

@io.micronaut.http.annotation.Controller
public class Controller {

    @Get("/")
    public String getModelAndView() {
        return "here";
    }

    @Get("/modelAndView")
    ModelAndView<String> modelAndView() {
        return new ModelAndView<String>("index.jsp", "");
    }
}
