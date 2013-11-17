package controllers;

import models.Video;
import play.mvc.Controller;
import play.mvc.Result;
import utils.Utils;
import views.html.upload;
import views.html.viewVideo;

public class Watch extends Controller {
    public static Result watch(String id) {
        Video v = Video.find.byId(id);
        if (v == null) {
            return redirect(routes.FourOhFour.fourOhFour());
        }

        if (v.views == null) {
            v.views = 1L;
        }
        v.views++;
        v.save();

        return ok(viewVideo.render(v, Utils.getUserOrNull(session("username"))));
    }


}
