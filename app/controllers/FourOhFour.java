package controllers;

import play.mvc.Controller;
import play.mvc.Result;
import utils.Utils;
import views.html.notfound;

public class FourOhFour extends Controller {
    public static Result fourOhFour() {
        return notFound(notfound.render(Utils.getUserByRequest(request())));
    }
}
