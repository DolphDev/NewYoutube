package controllers;

import models.User;
import org.apache.commons.lang3.RandomStringUtils;
import play.data.Form;
import play.data.validation.Constraints;
import play.mvc.Controller;
import play.mvc.Result;
import utils.Utils;
import views.html.login;
import views.html.signup;

import java.util.Map;
import java.util.regex.Pattern;

import static play.data.Form.form;

public class Login extends Controller {
    public static Result login() {
        return ok(login.render(Utils.getUserByRequest(request())));
    }

    public static Result authenticate() {
        Map<String, String[]> data = request().body().asFormUrlEncoded();

        if (!(data.containsKey("username") && !data.get("username")[0].equals(""))) {
            flash("error", "You did not specify a username!");
            return badRequest(login.render(Utils.getUserByRequest(request())));
        } else if (!(data.containsKey("password") && !data.get("password")[0].equals(""))) {
            flash("error", "You did not specify a password!");
            return badRequest(login.render(Utils.getUserByRequest(request())));
        }

        User u = User.authenticate(data.get("username")[0], data.get("password")[0]);

        if (u != null) {
            String sess = RandomStringUtils.random(32, true, true);
            response().setCookie("session-key", sess);
            u.sessionKey = sess;
            u.save();
            return redirect(routes.Application.index());
        } else {
            flash("error", "Invalid user or password!");
            return badRequest(signup.render(Utils.getUserByRequest(request())));
        }
    }

    public static Result logout() {
        session().clear();
        flash("success", "You've been logged out!");
        return redirect(routes.Login.login());
    }

    public static Result signup() {
        return ok(signup.render(Utils.getUserByRequest(request())));
    }

    public static Result signupDo() {
        Map<String, String[]> data = request().body().asFormUrlEncoded();
        if (!(data.containsKey("username") && !data.get("username")[0].equals(""))) {
            flash("error", "You did not specify a username!");
            return badRequest(signup.render(Utils.getUserByRequest(request())));
        } else if (!(data.containsKey("password") && !data.get("password")[0].equals(""))) {
            flash("error", "You did not specify a password!");
            return badRequest(signup.render(Utils.getUserByRequest(request())));
        } else if (!(data.containsKey("email") && !data.get("email")[0].equals(""))) {
            flash("error", "You did not specify an e-mail!");
            return badRequest(signup.render(Utils.getUserByRequest(request())));
        } else if (data.get("password")[0].length() < 7) {
            flash("error", "Password must be seven (7) characters or longer!");
            return badRequest(signup.render(Utils.getUserByRequest(request())));
        } else if (!Pattern.compile("^[a-zA-Z0-9_.+-]+@[a-zA-Z0-9-]+\\.[a-zA-Z0-9-.]+$").matcher(data.get("email")[0]).matches()) {
            flash("error", "That e-mail is invalid!");
            return badRequest(signup.render(Utils.getUserByRequest(request())));
        }
        User.signUp(data.get("email")[0], data.get("username")[0], data.get("password")[0]);
        flash("success", "You have been signed up!");
        return redirect(routes.Application.index());
    }
}
