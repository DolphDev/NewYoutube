package models;

import play.db.ebean.Model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import java.util.Set;

@Entity
public class VideoFile extends Model {
    @Id
    Integer id;

    String link;
    String mimetype;
    Set<String> codecs;

    @ManyToOne
    Video video;

    public static Model.Finder<Integer, VideoFile> find = new Model.Finder(Integer.class, VideoFile.class);
}
