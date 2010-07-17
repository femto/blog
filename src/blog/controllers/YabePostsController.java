package blog.controllers;

import blog.models.Post;
import com.scooterframework.orm.activerecord.ActiveRecord;
import com.scooterframework.orm.activerecord.ActiveRecordUtil;
import com.scooterframework.orm.misc.Paginator;
import com.scooterframework.web.config.Constants;
import com.scooterframework.web.util.R;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * # @copyright: please see original copyright notice
 *
 * @author: femto
 */
public class YabePostsController extends ApplicationController {
    /**
     * <tt>index</tt> method returns a list of <tt>posts</tt> records.
     * If the value of <tt>paged</tt> parameter is <tt>true</tt>, a paginated list is returned.
     */
    public String index() {
        if ("true".equals(params(Constants.PAGED))) {
            Paginator page = jdbcPaginator(Post.class, params());
            storeToRequest("post_page", page);

            return drymlHandle("yabe_posts", "paged_list");
        }
        storeToRequest("posts", findAll(Post.class));

        ActiveRecord postHome = ActiveRecordUtil.getHomeInstance(Post.class);
        List posts = postHome.findAll("", "order_by created_at: desc");
        System.out.println("frontPost");
        Map map = new HashMap();
        //map.put("posts",findAll(Post.class));
        map.put("posts", posts);

        if(posts.size() > 0) {
            map.put("frontPost", posts.remove(0));
        }
        map.put("olderPosts", posts); //notice now posts is one less than all posts


        return drymlHandle("yabe_posts", "index", map);


    }
    
    /**
     * <tt>show</tt> method returns a <tt>post</tt> record.
     */
    public String show() {
        ActiveRecord post = findRecordByPrimaryKey(Post.class, pkparams(Post.class));
        if (post == null) {
            flash("notice", "There is no post record with primary key as " + super.pkparams(Post.class));
        }
        else {
            storeToRequest("post", post);
        }
        
        return drymlHandle("yabe_posts", "show");

    }
    
    /**
     * <tt>add</tt> method prepares meta data for adding a new <tt>post</tt> record.
     */
    public String add() {
        storeToRequest("post", homeInstance(Post.class));
        return drymlHandle("yabe_posts", "add");
    }
    
    /**
     * <tt>create</tt> method creates a new <tt>post</tt> record.
     */
    public String create() {
        ActiveRecord newPost = null;
        try {
            newPost = newRecord(Post.class, params());
            newPost.save();
            flash("notice", "Post was successfully created.");
            
            return redirectTo(R.resourcePath("posts"));
        }
        catch(Exception ex) {
            log.error("Error in create() caused by " + ex.getMessage());
            flash("error", "There was a problem creating the post record.");
        }
        
        storeToRequest("post", newPost);
        return forwardTo(viewPath("add"));
    }
    
    /**
     * <tt>edit</tt> method prepares data for editing an existing <tt>post</tt> record.
     */
    public String edit() {
        storeToRequest("post", findRecordByPrimaryKey(Post.class, pkparams(Post.class)));
        return drymlHandle("yabe_posts", "edit");
    }
    
    /**
     * <tt>update</tt> method updates an existing <tt>post</tt> record.
     */
    public String update() {
        ActiveRecord post = null;
        try {
            post = findRecordByPrimaryKey(Post.class, pkparams(Post.class));
            if (post != null) {
                post.setData(params());
                post.update();
                flash("notice", "Post was successfully updated.");
                
                return redirectTo(R.resourceRecordPath("posts", post));
            }
            else {
                flash("notice", "There is no post record with primary key as " + pkparams(Post.class) + ".");
            }
        }
        catch(Exception ex) {
            log.error("Error in update() caused by " + ex.getMessage());
            flash("error", "There was a problem updating the post record.");
        }
        
        storeToRequest("post", post);
        return forwardTo(viewPath("edit"));
    }
    
    /**
     * <tt>delete</tt> method deletes a <tt>post</tt> record.
     */
    public String delete() {
        ActiveRecord post = findRecordByPrimaryKey(Post.class, pkparams(Post.class));
        if (post != null) {
            post.delete();
            flash("notice", "Post was successfully deleted.");
        }
        else {
            flash("notice", "There is no post record with primary key as " + pkparams(Post.class) + ".");
        }
        
        return redirectTo(R.resourcePath("posts"));
    }
}
