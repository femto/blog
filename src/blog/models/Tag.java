package blog.models;

import com.scooterframework.orm.activerecord.ActiveRecord;

/**
 * # @copyright: please see original copyright notice
 *
 * @author: femto
 */
public class Tag extends ActiveRecord {
    public void validatesRecord() {
    	validators().validatesPresenceOf("name");
    	//validators().validatesLengthMaximum("name", 10);
    	//validators().validatesLengthMaximum("content", 140);
        //Our posts are twitter friendly.
    }
    public void registerRelations() {
        hasMany("post_tags", cascade("delete"));
        
    }
}
