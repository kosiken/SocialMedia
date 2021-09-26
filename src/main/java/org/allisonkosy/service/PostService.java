package org.allisonkosy.service;

import org.allisonkosy.App;
import org.allisonkosy.entity.Post;
import org.allisonkosy.entity.QPost;

import org.allisonkosy.entity.User;


import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import java.util.List;


public class PostService extends AbstractService {

    public PostService(EntityManager entityManager) {
        super(entityManager);
    }

    public Post getAPost(Object object) {
        QPost post = QPost.post;
        Post c;
        try {
            if (object instanceof String) {
                String slug = (String) object;
                List<Post> postList;
                postList = queryFactory.selectFrom(post)
                        .where(post.slug.eq(slug))
                        .fetch();
              if(postList.size() > 0)  c = postList.get(0);
              else c = null;
            } else {
                Long id = (Long) object;
                c = queryFactory.selectFrom(post)
                        .where(post.id.eq(id))
                        .fetchOne();
            }
        }
        catch (Exception e) {
           App.logError(1);
            c = null;
        }
        return c;
    }

    public Post insertPost(User user, String body, String title) {
        Post post = null;
        EntityTransaction transaction = null;
        try {
            reinitialize();
            transaction = createTransaction();
            transaction.begin();
            post = new Post();
            post.setUser(user);
            post.setBody(body);
            post.setTitle(title);
            post.setSlug(title);
            entityManager.persist(post);
            transaction.commit();
        }

        catch (Exception err) {
            if (transaction != null) {
                try {
                    transaction.rollback();

                }
                catch (Exception err2) {
                    App.logger.error(err2.getMessage());
                    return null;
                }

            }
            err.printStackTrace();
            post = null;
        }
        finally {

//            close("Post");
            if(post!= null) {
                App.logger.info("Created " + post);
            }
        }
        return post;


    }

    public List<Post> getAllPosts( User user) {
        QPost post = QPost.post;
        List<Post> posts =null;
        try {
            if(user != null) {
                posts = queryFactory.selectFrom(post)
                        .where(post.user.eq(user)).fetch();
            }
          else {
                posts = queryFactory.selectFrom(post)
                        .where().fetch();
            }


        }
        catch (Exception e) {
            App.logError(1);
            App.logger.error(e);
        }
        return posts;
    }
}
