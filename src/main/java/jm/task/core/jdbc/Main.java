package jm.task.core.jdbc;

import jm.task.core.jdbc.dao.UserDaoHibernateImpl;
import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.service.UserService;
import jm.task.core.jdbc.service.UserServiceImpl;
import jm.task.core.jdbc.util.Util;
import org.hibernate.SessionFactory;


import java.util.List;

public class Main {
    public static void main(String[] args) {


        UserService userService = new UserServiceImpl();
        userService.createUsersTable();
        userService.saveUser("Bob1", "Bob", (byte) 35);
        userService.saveUser("Bob2", "Bob", (byte) 36);
        userService.saveUser("Bob3", "Bob", (byte) 37);
        userService.saveUser("Bob4", "Bob", (byte) 38);
        List<User> users = userService.getAllUsers();
        for (User user : users) {
            System.out.println(user);
        }
        userService.cleanUsersTable();
        userService.dropUsersTable();// реализуйте алгоритм здесь
    }
}
