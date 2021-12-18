package com.gunt.zhzh.dao;


import com.gunt.zhzh.entity.User;

import org.springframework.stereotype.Repository;

import javax.persistence.*;

import java.util.List;

@Repository
public class UserDAOImpl implements UserDAO {
    User user = new User();

    @PersistenceUnit
    EntityManagerFactory entityManager;


    @Override
    public List<User> getAllUsers() {
        EntityManager session = entityManager.createEntityManager();
        session.getTransaction().begin();
        List<User> allUsers = session.createQuery("from User", User.class).getResultList();
        return allUsers;
    }


    @Override
    public void saveUser(User user) {
        EntityManager session = entityManager.createEntityManager();
        try {
            session.getTransaction().begin();
            session.merge(user);
            session.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            session.close();
        }
    }

    @Override
    public User getUser(int id) {
        EntityManager session = entityManager.createEntityManager();
            try {
                session.getTransaction().begin();
                user = session.find(User.class, id);
                session.persist(user);
                session.getTransaction().commit();
            }catch (Exception e) {
                e.printStackTrace();
            }finally {
                session.close();
            }
            return user;
    }

    @Override
    public void deleteUser(int id) {
        EntityManager session = entityManager.createEntityManager();
        try {
            session.getTransaction().begin();
            Query query = session.createQuery("delete from User where id=:usId");
            query.setParameter("usId", id);
            query.executeUpdate();
            session.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            session.close();
        }
    }
}

