package utils;

import models.User;
import org.h2.util.StringUtils;
import play.mvc.Http;
import play.mvc.Http.Request;
import play.mvc.Result;
import play.mvc.Results;
import views.html.index;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Utils {
    public static String hashPassword(String password, String salt) {
        String hashedPass = password + salt;
        try {
            for (int i=0;i<20000;i++) {
                MessageDigest md = MessageDigest.getInstance("SHA-256");
                md.update(hashedPass.getBytes("UTF-8"));
                byte[] digest = md.digest();
                hashedPass = StringUtils.convertBytesToHex(digest);
            }
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        System.out.println(hashedPass);

        return hashedPass;
    }

    public static boolean isEmailValid(String email) {
        String patternStr = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
        Pattern pat = Pattern.compile(patternStr);
        Matcher matcher = pat.matcher(email);
        return matcher.matches();
    }

    public static String getSessionKey(Request request) {
        Http.Cookie cookie = request.cookie("session-key");
        if (cookie == null) {
            return null;
        } else {
            return cookie.value();
        }
    }

    public static User getUserByRequest(Request request) {
        return User.getBySessionKey(Utils.getSessionKey(request));
    }
}
