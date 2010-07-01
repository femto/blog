package blog.controllers;

import blog.models.Post;

/**
 * WelcomeController class handles welcome related access.
 */
public class WelcomeController extends ApplicationController {
    /**
     * sayit method
     */
    public String sayit() {
        log.info("sayit:" + this.getClass().getClassLoader());
        flash("notice", "succesfully saved!");
        storeToRequest("@content", "fun1");
        return null;
    }

    public String sayone() {
        return html(new Long(Post.count()).toString()) ;
    }

}