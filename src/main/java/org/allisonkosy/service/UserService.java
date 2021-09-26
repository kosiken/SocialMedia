package org.allisonkosy.service;
import com.querydsl.core.NonUniqueResultException;
import com.querydsl.jpa.impl.JPAQuery;
import org.allisonkosy.App;
import org.allisonkosy.entity.*;
import org.hibernate.exception.ConstraintViolationException;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.RollbackException;
import java.util.List;
import java.util.Locale;
//import java.util.List;

public class UserService extends AbstractService {
    public UserService(EntityManager entityManager) {
        super(entityManager);
    }

    public User getUser(Object object) {

        QUser user = QUser.user;
        User c;
      try{
          if(object instanceof String) {
              String username = (String) object;
              username = username.toLowerCase(Locale.ROOT);
              c = queryFactory.selectFrom(user)
                      .where(user.username.eq(username))
                      .fetchOne();
          }
          else {
              Long id = (Long) object;
              c = queryFactory.selectFrom(user)
                      .where(user.id.eq(id))
                      .fetchOne();
          }
      }
         catch (NonUniqueResultException e) {
             App.logError(1);
            c = null;
        }
        return c;
    }

    public User insertUser(String username, String password) {
        User user = null;
        EntityTransaction transaction = null;
        try {
            reinitialize();
            transaction = createTransaction();
            transaction.begin();

            user   = new User();
            user.setUsername(username);
            user.setPassword(password);
            entityManager.persist(user);
            transaction.commit();
        }
        catch (ConstraintViolationException | RollbackException exception) {
            if (transaction != null) {
                transaction.rollback();

            }
            App.logError(2);

            user = null;
        }
        catch (Exception err) {
            if (transaction != null) {
                transaction.rollback();

            }
            err.printStackTrace();

            user = null;
        }
        finally {

//           close("User");
           if(user != null) {
               App.logger.info("Created " + user.toString() );
           }
        }
        return user;


    }

    public List<User> getAllUsers() {
        QUser user = QUser.user;
        List<User> users =null;
        try {
            users = queryFactory.selectFrom(user)
                    .where().fetch();


        }
        catch (Exception e) {
            App.logError(1);
            App.logger.error(e);
        }
        return users;
    }
}
