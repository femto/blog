package blog.controllers;

import blog.models.Post;
import com.scooterframework.web.config.WebConfig;
import template.DrymlTemplateHandler;

/**
 * WelcomeController class handles welcome related access.
 */
public class WelcomeController extends ApplicationController {
    /**
     * sayit method
     */
    public String sayit() {
        flash("notice", "succesfully saved!");
        storeToRequest("@content", "fun1");
        StringBuilder result = new StringBuilder();
        String uri = getViewURI("welcome", "sayit");
        System.out.println(uri);
        uri = WebConfig.getRealPath() + uri;
        new DrymlTemplateHandler().handle(uri, result);

        return html(result.toString());
    }

    public String sayone() {
        return html(new Long(Post.count()).toString()) ;
    }

}