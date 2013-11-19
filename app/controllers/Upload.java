package controllers;

import models.User;
import models.Video;
import models.VideoFile;
import play.mvc.Controller;
import play.mvc.Http;
import play.mvc.Result;
import utils.Utils;
import views.html.login;
import views.html.upload;

import java.io.File;
import java.util.HashSet;
import java.util.Set;

public class Upload extends Controller {
    public static Result upload() {
        if (Utils.getUserByRequest(request()) == null) {
            flash("error", "You are not logged in! Log in now.");
            return unauthorized(login.render(Utils.getUserByRequest(request())));
        }
        return ok(upload.render(Utils.getUserByRequest(request())));
    }

    public static Result uploadDo() {
        // Primarily accessed via AJAX
        if (Utils.getUserByRequest(request()) == null) {
            return unauthorized("Not logged in");
        }
        Http.MultipartFormData body = request().body().asMultipartFormData();
        Http.MultipartFormData.FilePart videoFile = body.getFile("file");

        if (videoFile != null) {
            String fileName = videoFile.getFilename();
            String contentType = videoFile.getContentType();
            File file = videoFile.getFile();
            File newFile = new File(file.getParentFile(), fileName);
            file.renameTo(newFile);
            file = newFile;

            String title = body.asFormUrlEncoded().get("title")[0];
            String desc = body.asFormUrlEncoded().get("desc")[0];

            if (title.equals("")) {
                return badRequest("Title must not be blank!");
            }
            if (desc.equals("")) {
                return badRequest("Description must not be blank!");
            }

            User u = Utils.getUserByRequest(request());

            VideoFile vf = new VideoFile();
            vf.codecs = new HashSet<>();
            vf.link = "file:///" + file.getAbsolutePath();
            vf.mimetype = contentType;

            Set<VideoFile> vfs = new HashSet<>();
            vfs.add(vf);

            Video v = Video.upload(title, desc, u, vfs);
            vf.video = v;
            vf.save();
            v.save();
            return ok(v.id);
        } else {
            flash("error", "You did not choose a file!");
            return badRequest(upload.render(Utils.getUserByRequest(request())));
        }
    }
}
