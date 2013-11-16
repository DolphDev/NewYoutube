package controllers;

import org.apache.commons.lang3.StringUtils;
import play.mvc.Controller;
import play.mvc.Result;
import utils.Utils;
import views.html.*;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.Map;

public class Upload extends Controller {
    public static Result uploadIndex() {
        return ok(uploadIndex.render(Utils.getUserOrNull(request())));
    }

    public static Result uploadFile() {
        Map<String, String[]> formData = request().body().asFormUrlEncoded();

        try {
            String title = URLDecoder.decode(StringUtils.join(formData.get("title")), "UTF-8");
            String desc = URLDecoder.decode(StringUtils.join(formData.get("description")), "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        return redirect(routes.Application.index());
        //TODO: Make uploading
    }
}
