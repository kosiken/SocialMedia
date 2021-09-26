package org.allisonkosy.service;

import com.querydsl.core.types.EntityPath;
import com.querydsl.jpa.JPAQueryBase;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.allisonkosy.App;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

public class AbstractService {

    // The classes inheriting from this class would be used to query the database 
    // This is to allow some abstraction from the Server class
    protected EntityManager entityManager;
    protected final JPAQueryFactory queryFactory;
    AbstractService(EntityManager entityManager) {
        this.entityManager = entityManager;
        queryFactory = new JPAQueryFactory(entityManager);
    }



    public void setEntityManager(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    protected void reinitialize() {
        // We have to create new entity managers if the current one is closed
        if(!this.entityManager.isOpen()) {
            this.entityManager = App.entityManagerFactory.createEntityManager();
        }
    }

    protected void close(String entity) {
        App.logger.info("Closing " + entity);
        this.entityManager.close();
    }

    protected EntityTransaction createTransaction() {
       EntityTransaction transaction = entityManager.getTransaction();
        return transaction;
    }


}
