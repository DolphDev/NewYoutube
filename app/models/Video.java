package models;

import play.db.ebean.Model;

import javax.persistence.*;
import java.util.Date;
import java.util.Set;

@Entity
public class Video extends Model {
    @Id
    String id;

    String title;
    String description;
    Integer likes;
    Integer dislikes;

    Date uploaded;

    @ManyToOne
    User uploader;

    String thumbnailLink;

    @ManyToMany(mappedBy = "videos")
    Set<Tag> tags;

    @OneToMany(mappedBy = "video")
    Set<Comment> comments;

    @OneToMany(mappedBy = "video")
    Set<VideoFile> videoFiles;

    public static Model.Finder<String, Video> find = new Model.Finder(String.class, Video.class);

    public Tag addTag(String tag) {
        Tag t = Tag.find.byId(tag);
        if (t == null) {
            t = new Tag(tag);
        }
        t.videos.add(this);
        t.save();
        return t;
    }

    public static Video upload(String title, String description, User uploader, Set<VideoFile> files) {
        Video v = new Video();
        v.title = title;
        v.description = description;
        v.uploader = uploader;
        v.videoFiles = files;
        v.uploaded = new Date();
        v.save();
        return v;
    }

    public void like(User user) {
        user.likeVideo(this);
    }

    public void dislike(User user) {
        user.dislikeVideo(this);
    }
}
