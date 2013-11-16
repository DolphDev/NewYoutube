package utils;

import models.User;
import org.h2.util.StringUtils;
import play.mvc.Http.Request;
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

    public static User getUserOrNull(Request req) {
        if (req.username() != null) {
            User u = User.find.where().eq("username", req.username()).findUnique();
            return u;
        } else {
            return null;
        }
    }
}
