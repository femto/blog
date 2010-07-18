package blog.models;

import com.scooterframework.orm.activerecord.ActiveRecord;

/**
 * # @copyright: please see original copyright notice
 *
 * @author: femto
 */
public class PostTag extends ActiveRecord {

    public void registerRelations() {
        belongsTo("post");
        belongsTo("tag");
    }
}
