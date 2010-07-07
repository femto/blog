package blog.controllers;

import blog.models.Post;
import com.scooterframework.web.config.WebConfig;
import template.ApplicationConfig;
import template.DrymlTemplateHandler;

/**
 * WelcomeController class handles welcome related access.
 */
public class WelcomeController extends ApplicationController {
    /**
     * sayit method
     */
    public String sayit() {

       return drymlHandle("welcome", "sayit");
    }

    public String sayone() {
        return html(new Long(Post.count()).toString()) ;
    }

}