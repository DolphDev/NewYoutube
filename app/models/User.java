package models;

import org.h2.util.StringUtils;
import play.db.ebean.Model;
import utils.Utils;

import javax.persistence.*;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Date;
import java.util.Set;

@Entity
public class User extends Model {
    @Id
    public Integer id;

    public String username;
    public String password;
    public String email;

    public String customCss;

    public Date created;

    public Set<User> subscriptions;

    public Set<User> subscribers;

    @OneToMany(mappedBy = "uploader")
    public Set<Video> videos;

    @OneToMany(mappedBy = "author")
    public Set<Comment> comments;


    @OneToMany
    public Set<Video> likedVideos;

    @OneToMany
    public Set<Video> dislikedVideos;

    @OneToMany
    public Set<Comment> likedComments;

    @OneToMany
    public Set<Comment> dislikedComments;

    public static User signUp(String email, String username, String password) {
        User u = new User();
        u.username = username;
        u.password = Utils.hashPassword(password, username);
        u.email = email;
        u.created = new Date();
        u.save();
        return u;
    }

    public static Model.Finder<Integer, User> find = new Model.Finder(Integer.class, User.class);

    public static User authenticate(String username, String password) {
        String hashedPass = Utils.hashPassword(password, username);

        return find.where().eq("username", username).eq("password", hashedPass).findUnique();
    }

    public void likeVideo(Video vid) {
        if (!likedVideos.contains(vid)) {
            vid.likes++;
            likedVideos.add(vid);
        }
        if (dislikedVideos.contains(vid)) {
            vid.dislikes--;
            dislikedVideos.remove(vid);
        }
    }

    public void dislikeVideo(Video vid) {
        if (!dislikedVideos.contains(vid)) {
            vid.dislikes++;
            likedVideos.add(vid);
        }
        if (likedVideos.contains(vid)) {
            vid.likes--;
            likedVideos.remove(vid);
        }
    }

    public void likeComment(Comment com) {
        if (!likedComments.contains(com)) {
            com.likes++;
            likedComments.add(com);
        }
        if (dislikedComments.contains(com)) {
            com.dislikes--;
            dislikedComments.remove(com);
        }
    }

    public void dislikeComment(Comment com) {
        if (!dislikedComments.contains(com)) {
            com.dislikes++;
            dislikedComments.add(com);
        }
        if (likedComments.contains(com)) {
            com.likes--;
            likedComments.remove(com);
        }
    }
}
