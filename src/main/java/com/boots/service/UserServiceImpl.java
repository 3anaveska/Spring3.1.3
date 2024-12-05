package com.boots.service;

import com.boots.dao.UserDao;
import com.boots.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;



@Service
@Transactional
public class UserServiceImpl implements UserService {


    private final UserDao userDao;
    private final RoleService roleService;

    @Autowired
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(12);
    }

    @Autowired
    public UserServiceImpl(UserDao userDao, RoleService roleService) {
        this.roleService = roleService;
        this.userDao = userDao;
    }


    @Transactional(readOnly = true)
    public User getUserByEmail(String email) {

        return userDao.getUserByEmail(email);
    }

    @Override
    public void addUser(User user, String[] roles) {
        user.setRoles(roleService.getSetOfRoles(roles));
        userDao.addUser(user);
        passwordEncoder();
    }

    @Transactional(readOnly = true)
    public User getUserById(Long id, User user, String[] roles) {
        user.setRoles(roleService.getSetOfRoles(roles));
        return userDao.getUserById(id, user, roles);
    }

    @Override
    @Transactional
    public void updateUser(User user, String[] roles) {
        user.setRoles(roleService.getSetOfRoles(roles));
        userDao.updateUser(user, roles);
    }

    @Override
    @Transactional
    public void removeUserById(Long id) {

        userDao.removeUserById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<User> listUsers() {

        return userDao.listUsers();
    }


}
