package models;

import play.db.ebean.Model;

import javax.persistence.*;
import java.util.Date;
import java.util.Set;

@Entity
public class Video extends Model {
    @Id
    public String id;

    public String title;
    public String description;
    public Integer likes = 0;
    public Integer dislikes = 0;

    public Date uploaded = new Date();

    public Long views = 0L;

    @ManyToOne
    public User uploader;

    public String thumbnailLink;

    @ManyToMany(mappedBy = "videos")
    public Set<Tag> tags;

    @OneToMany(mappedBy = "video")
    @OrderBy("posted DESC")
    public Set<Comment> comments;

    @OneToMany(mappedBy = "video")
    public Set<VideoFile> videoFiles;

    public static Model.Finder<String, Video> find = new Model.Finder(String.class, Video.class);

    public Tag addTag(String tag) {
        Tag t = Tag.find.byId(tag);
        if (t == null) {
            t = new Tag(tag);
        }
        t.videos.add(this);
        t.save();
        tags.add(t);
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

        uploader.videos.add(v);
        return v;
    }

    public void like(User user) {
        user.likeVideo(this);
    }

    public void dislike(User user) {
        user.dislikeVideo(this);
    }
}
