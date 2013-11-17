package controllers;

import models.User;
import play.data.Form;
import play.data.validation.Constraints;
import play.mvc.Controller;
import play.mvc.Result;
import utils.Utils;
import views.html.login;
import views.html.signup;

import static play.data.Form.form;

public class Login extends Controller {
    public static Result login() {
        return ok(login.render(form(LoginForm.class), Utils.getUserOrNull(session("username"))));
    }

    public static Result authenticate() {
        Form<LoginForm> loginForm = form(LoginForm.class).bindFromRequest();
        if (loginForm.hasErrors()) {
            return badRequest(login.render(loginForm, Utils.getUserOrNull(session("username"))));
        } else {
            session().clear();
            session("username", loginForm.get().username);
            return redirect(routes.Application.index());
        }
    }

    public static Result logout() {
        session().clear();
        flash("success", "You've been logged out!");
        return redirect(routes.Login.login());
    }

    public static Result signup() {
        return ok(signup.render(form(SignUpForm.class), Utils.getUserOrNull(session("username"))));
    }

    public static Result signupDo() {
        Form<SignUpForm> signupForm = form(SignUpForm.class).bindFromRequest();
        if (signupForm.hasErrors()) {
            return badRequest(signup.render(signupForm, Utils.getUserOrNull(session("username"))));
        } else {
            User.signUp(signupForm.get().email, signupForm.get().username, signupForm.get().password);
            flash("success", "You have now been signed up! Now, log in with your credentials.");
            return redirect(routes.Login.login());
        }
    }

    public static class LoginForm {
        public String username;
        public String password;

        public String validate() {
            if (username == "") {
                return "Username must not be blank";
            }
            if (password == "") {
                return "Password must not be blank";
            }
            if (User.authenticate(username, password) == null) {
                return "Invalid user or password";
            }
            return null;
        }
    }

    public static class SignUpForm {
        @Constraints.Required
        public String username;

        @Constraints.Required
        public String password;

        @Constraints.Required
        @Constraints.Email
        public String email;

        public String validate() {
            if (username == "") {
                return "Username must not be blank";
            }
            if (password == "") {
                return "Password must not be blank";
            }
            if (email == "") {
                return "E-mail must not be blank";
            }
            if (password.length() < 8) {
                return "Password must be longer than 8 characters";
            }
            if (User.find.where().eq("username", username).findUnique() != null) {
                return "That user already exists!";
            }
            return null;
        }
    }
}
