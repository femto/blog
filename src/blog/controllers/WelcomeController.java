package blog.controllers;

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
        return null;
    }

}