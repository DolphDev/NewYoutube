package models;

import play.db.ebean.Model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import java.util.Date;

@Entity
public class Comment extends Model {
    @Id
    Integer id;

    String content;

    Integer likes;
    Integer dislikes;

    Date posted;

    @ManyToOne
    User author;

    @ManyToOne
    Video video;

    public static Model.Finder<Integer, Comment> find = new Model.Finder(Integer.class, Comment.class);

    public void like(User user) {
        user.likeComment(this);
    }

    public void dislike(User user) {
        user.dislikeComment(this);
    }

    public static Comment post(User user, Video video, String content) {
        Comment c = new Comment();
        c.video = video;
        c.content = content;
        c.posted = new Date();
        c.save();
        return c;
    }
}