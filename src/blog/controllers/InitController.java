package blog.controllers;

import blog.models.Comment;
import blog.models.Post;

//import blog.models.Post;

public class InitController extends ApplicationController {
    /**
     * sayit method
     */
    public String index() {
        //flash("notice", "succesfully saved!");
        //storeToRequest("@content", "fun1");
        //return null;
        //return text("init done");
        if (Post.count() < 3) { //do init
            Post.deleteAll();   //warning, override the original data
            Comment.deleteAll();

            Post post0 = new Post();
            post0.setData("name", "first post");
            post0.setData("title", "this is first post");
            post0.setData("content", "this is first post");
            post0.save();

            Post post1 = new Post();
            post1.setData("name", "second post");
            post1.setData("title", "this is second post");
            post1.setData("content", "this is second post");
            post1.save();

            Post post = new Post();
            post.setData("name", "third post");
            post.setData("title", "this is third post");
            post.setData("content", "this is third post");
            post.save();

            Comment comment = new Comment();
            comment.setData("commenter", "Joe");
            comment.setData("body", "I like your post");
            comment.associated("post").attach(post);
            comment.save();

            comment = new Comment();
            comment.setData("commenter", "Mike");
            comment.setData("body", "Love it!");
            comment.associated("post").attach(post0);
            comment.save();

            comment = new Comment();
            comment.setData("commenter", "Tom");
            comment.setData("body", "This post is useless..");
            comment.associated("post").attach(post1);
            comment.save();

            


            //return html(new Post().getTableName());

        }
        return html("init done");
    }

}