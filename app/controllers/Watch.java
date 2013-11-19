package controllers;

import models.User;
import models.Video;
import play.mvc.Controller;
import play.mvc.Result;
import play.mvc.Security;
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

        return ok(viewVideo.render(v, Utils.getUserByRequest(request())));
    }

    public static Result likeVideo(String id) {
        // Primarily accessed via AJAX
        if (Utils.getUserByRequest(request()) == null) {
            return unauthorized("Not logged in");
        }
        Video v = Video.find.byId(id);
        if (v == null) {
            return redirect(routes.FourOhFour.fourOhFour());
        }
        User u = Utils.getUserByRequest(request());

        u.likeVideo(v);
        return ok();
    }

    public static Result dislikeVideo(String id) {
        // Primarily accessed via AJAX
        if (Utils.getUserByRequest(request()) == null) {
            return unauthorized("Not logged in");
        }
        Video v = Video.find.byId(id);
        if (v == null) {
            return redirect(routes.FourOhFour.fourOhFour());
        }
        User u = Utils.getUserByRequest(request());

        u.dislikeVideo(v);
        return ok();
    }
}
