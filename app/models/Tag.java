package models;

import play.db.ebean.Model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import java.util.Set;

@Entity
public class Tag extends Model {
    @Id
    String name;

    @ManyToMany
    Set<Video> videos;

    public static Model.Finder<String, Tag> find = new Model.Finder(String.class, Tag.class);

    public Tag(String name) {
        this.name = name;
    }
}
