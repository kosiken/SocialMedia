package org.allisonkosy.runner;

import org.allisonkosy.App;
import org.allisonkosy.entity.*;
import org.allisonkosy.service.CommentService;
import org.allisonkosy.service.PostService;
import org.allisonkosy.service.UserService;

import java.util.ArrayList;
import java.util.List;

public class Server {
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
        return postService.insertPost(user,body,title);
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

//    public ArrayList<User> createBulk(List<String[]> users) {
//        ArrayList<User> userArrayList = new ArrayList<>();
//        for (String[] s:
//             users) {
//            try {
//                userArrayList.add(createUser(s[0], s[1]));
//            }
//            catch (ArrayIndexOutOfBoundsException exception) {
//                App.logger.error(exception.getMessage());
//                userArrayList = new ArrayList<>();
//                break;
//            }
//        }
//        return userArrayList;
//    }
}
