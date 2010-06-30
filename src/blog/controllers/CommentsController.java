package blog.controllers;

import com.scooterframework.orm.activerecord.ActiveRecord;
import com.scooterframework.orm.misc.Paginator;
import com.scooterframework.web.config.Constants;
import com.scooterframework.web.util.R;

import blog.models.Comment;

/**
 * CommentsController class handles comments related access.
 */
public class CommentsController extends ApplicationController {


    
    /**
     * <tt>create</tt> method creates a new <tt>comment</tt> record.
     */
    public String create() {
        ActiveRecord newComment = null;
        try {
            
            newComment = newRecord(Comment.class, params());
            newComment.save();
            flash("notice", "Comment was successfully created.");
            
            return redirectTo(R.resourcePath("comments"));
        }
        catch(Exception ex) {
            log.error("Error in create() caused by " + ex.getMessage());
            flash("error", "There was a problem creating the comment record.");
        }
        
        storeToRequest("comment", newComment);
        return forwardTo(viewPath("add"));
    }
    
    

}