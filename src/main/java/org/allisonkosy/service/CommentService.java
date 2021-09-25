package org.allisonkosy.service;
import org.allisonkosy.App;
import org.allisonkosy.entity.Comment;
import org.allisonkosy.entity.Post;
import org.allisonkosy.entity.User;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

public class CommentService extends AbstractService {

    public CommentService(EntityManager entityManager) {
        super(entityManager);
    }

   public Comment insertComment(User user, Post post, String body) {
       Comment comment = null;
       EntityTransaction transaction = null;

       try {
           reinitialize();
           transaction = createTransaction();
           transaction.begin();
           comment = new Comment();
           comment.setBody(body);
           comment.setPost(post);
           comment.setUser(user);
           entityManager.persist(comment);
           transaction.commit();

       } catch (Exception err) {
           if (transaction != null) {
               transaction.rollback();

           }
           err.printStackTrace();
           comment = null;
       }
       finally {

//           close("Comment");
           if(comment != null) {
               App.logger.info("Created " + comment);
           }
       }
       return comment;
   }
}
