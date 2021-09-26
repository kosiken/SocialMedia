package org.allisonkosy.runner;

import org.allisonkosy.App;
import org.allisonkosy.entity.*;
import org.allisonkosy.service.CommentService;
import org.allisonkosy.service.PostService;
import org.allisonkosy.service.UserService;

import java.util.ArrayList;
import java.util.List;

public class Server {
    // Services handle all queries relating to their respective entities
    private final  UserService userService = new UserService(App.entityManagerFactory.createEntityManager());
    private final PostService postService = new PostService(App.entityManagerFactory.createEntityManager());
    private final CommentService commentService = new CommentService(App.entityManagerFactory.createEntityManager());

    public User createUser(String username, String password) {

       return userService.insertUser(username, password);

    }

    public User getAUser(Object object) {
        User user = userService.getUser(object);
       if(user != null) App.logger.info(user.toString());
       else {
        
           App.logError(0);
       }
        return user;
    }


    private Post createPost(User user, String body, String title) {
        String realBody = body;
        if(realBody.length() < 5) {
            realBody = body + " padding to avoid errors";
        }
        return postService.insertPost(user,realBody,title);
    }
    public Post createPost(String username, String body, String title) {
        User u = getAUser(username);
        if(u == null) {
            return null;
        }

        else {

            return createPost(u, body, title);
        }
    }

    public Post createPost(int id, String body, String title) {
        User u = getAUser(Long.valueOf(id));
        if(u == null) {
            return null;
        }
        else {
            return createPost(u, body, title);
        }
    }

    public Post createPost(Long id, String body, String title) {
        User u = getAUser(id);
        if(u == null) {
            return null;
        }
        else {
            return createPost(u, body, title);
        }
    }
    public Post findPost(int id) {
        return postService.getAPost(Long.valueOf((long) id));

    }

    public Post findPost(String slug) {
        Post p = postService.getAPost(slug);
        if(p != null) App.logger.info(p.toString());
        else {
            App.logError(0);
        }
        return p;

    }

    private Comment addComment(User user, Post post, String body) {
        return commentService.insertComment(user, post, body);
    }

    public Comment addComment(int userId, int postId, String body) {
        Post post = findPost(postId);
        User user = getAUser(Long.valueOf((long) userId));
        if(user == null || post == null) {
            return null;
        }
        else {
            return addComment(user, post, body);
        }
    }
    private List<Comment> getComments(Object object) {
        return commentService.getComments(object);
    }
    public List<Comment> getComments (int postId) {
        Post post = findPost(postId);
        if(post == null) {
            return null;
        }
        return getComments(post);

    }

    public List<Comment> getCommentsByUser (int userId) {
        User user = getAUser(Long.valueOf((long) userId));
        if(user == null) {
            return  null;
        }

        return getComments(user);

    }

    public List<Post> getAllPosts() {
        return postService.getAllPosts(null);
    }
    public List<Post> getAllPosts(int userId) {
        User user = getAUser(Long.valueOf((long) userId));
        if(user == null) {
            return  null;
        }
        return postService.getAllPosts(user);
    }

    public List<User> getAllUsers() {
        return userService.getAllUsers();
    }


}
