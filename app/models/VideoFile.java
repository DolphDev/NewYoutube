package models;

import play.db.ebean.Model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import java.util.Set;

@Entity
public class VideoFile extends Model {
    @Id
    public Integer id;

    public String link;
    public String mimetype;
    public Set<String> codecs;

    @ManyToOne
    public Video video;

    public static Model.Finder<Integer, VideoFile> find = new Model.Finder(Integer.class, VideoFile.class);
}
