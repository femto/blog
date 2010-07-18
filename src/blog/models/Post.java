package blog.models;

import com.scooterframework.orm.activerecord.ActiveRecord;

import java.util.Date;
import java.util.List;

/**
 * Post class represents a post record in database.
 */
public class Post extends ActiveRecord {
    public void validatesRecord() {
    	validators().validatesPresenceOf("name, title, content");
    	//validators().validatesLengthMaximum("name", 10);
    	//validators().validatesLengthMaximum("content", 140);
        //Our posts are twitter friendly.
    }
    public void registerRelations() {
        
        hasMany("comments", cascade("delete"));
        belongsTo("user");

        hasMany("post_tags");
        hasManyThrough("tags", "post_tags");
    }

    //private Integer id;
    //private String title;


    public Integer getId() {
        return (Integer) getField("id");
    }

    public String getName() {
        String name = (String) getField("name");
        return name;
    }

    public String getTitle() {
        String title = (String) getField("title");
        return title;
    }

    public String getContent() {
        String title = (String) getField("content");
        return title;
    }

    public Date getCreated_at() {
        return (Date) getField("created_at");
    }

    public User getAuthor() {

        return (User) associated("user").getRecord();
    }

    public List getComments() {
        return (List)allAssociated("comments").getRecords();
    }

    public List getTags() {
        return (List)allAssociated("tags").getRecords();
    }

//    public void setTitle(String title) {
//        this.title = title;
//    }
}