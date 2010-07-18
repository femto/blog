package blog.models;

import com.scooterframework.orm.activerecord.ActiveRecord;

/**
 * # @copyright: please see original copyright notice
 *
 * @author: femto
 */
public class User extends ActiveRecord {
    public void validatesRecord() {
    	validators().validatesPresenceOf("name, password");
    	//validators().validatesLengthMaximum("name", 10);
    	validators().validatesIsEmail("email");

    }
    public void registerRelations() {

        hasMany("posts", cascade("delete"));
    }

    public String getName() {
        return (String) getField("name");
    }
}
