package models;

import play.db.ebean.Model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import java.util.Date;
import java.util.List;

@Entity
public class Comment extends Model {
    @Id
    public Integer id;

    public String content;

    public Integer likes;
    public Integer dislikes;

    public Date posted;

    @ManyToOne
    public User author;

    @ManyToOne
    public Video video;

    @ManyToMany
    public List<User> likedBy;

    @ManyToMany
    public List<User> dislikedBy;

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
