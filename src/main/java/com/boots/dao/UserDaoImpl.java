package com.boots.dao;

import com.boots.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.EntityNotFoundException;
import javax.persistence.PersistenceContext;
import java.util.List;


@Repository
public class UserDaoImpl implements UserDao {

    @PersistenceContext
    private final EntityManager entityManager;

    @Autowired
    public UserDaoImpl(EntityManager entityManager) {

        this.entityManager = entityManager;
    }

    @Override
    public User getUserByEmail(String email) {
        return entityManager.createQuery(
                        "SELECT user FROM User user join fetch  user.roles WHERE user.email =:email", User.class)
                .setParameter("email", email)
                .getSingleResult();
    }

    @Override
    public void addUser(User user) {

        entityManager.persist(user);
    }

    @Override
    public User getUserById(Long id, User user, String[] roles) {
        try {
            return entityManager.find(User.class, id);
        } catch (EntityNotFoundException e) {
            throw new EntityNotFoundException(String.format("Не существует id", id));
        }

    }

    @Override
    public void updateUser(User user, String[] roles) {
        entityManager.merge(user);
    }

    @Override
    public void removeUserById(Long id) {
        try {
            entityManager.remove(entityManager.find(User.class, id));
        } catch (EntityNotFoundException e) {
            throw new EntityNotFoundException(String.format("Не существует id", id));
        }

    }

    @Override
    public List<User> listUsers() {

        return entityManager.createQuery("From User", User.class).getResultList();
    }
}
