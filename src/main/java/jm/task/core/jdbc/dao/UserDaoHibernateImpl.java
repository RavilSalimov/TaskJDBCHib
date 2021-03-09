package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import java.util.List;

public class UserDaoHibernateImpl implements UserDao {

    public UserDaoHibernateImpl() {

    }


    @Override
    public void createUsersTable() {
        SessionFactory sessionFactory = new Util().getSessionFactory();
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        session.createSQLQuery("CREATE TABLE IF NOT EXISTS user (id INT PRIMARY KEY AUTO_INCREMENT, " +
                "name VARCHAR(20) NOT NULL , lastName VARCHAR(20) NOT NULL , age INT(3) NOT NULL) " +
                "DEFAULT CHARSET=utf8")
                .executeUpdate();
        transaction.commit();
        session.close();
        sessionFactory.close();
    }

    @Override
    public void dropUsersTable() {
        SessionFactory sessionFactory = new Util().getSessionFactory();
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        session.createSQLQuery("DROP TABLE IF EXISTS user").executeUpdate();
        transaction.commit();
        session.close();
        sessionFactory.close();
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        User user = new User(name, lastName, age);
        SessionFactory sessionFactory = new Util().getSessionFactory();
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        session.save(user);
        transaction.commit();
        session.close();
        sessionFactory.close();
        System.out.println("User с именем " + name + " добавлен в базу данных.");

    }

    @Override
    public void removeUserById(long id) {
        SessionFactory sessionFactory = new Util().getSessionFactory();
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        User user = (User) session.load(User.class, id);
        session.delete(user);
        transaction.commit();
        session.close();
        sessionFactory.close();
    }

    @Override
    public List<User> getAllUsers() {
        SessionFactory sessionFactory = new Util().getSessionFactory();
        Session session = sessionFactory.openSession();
        List<User> l = session.createQuery("from User").list();
        return l;//добавил SQL в запрос
    }

    @Override
    public void cleanUsersTable() {
        SessionFactory sessionFactory = new Util().getSessionFactory();
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        session.createSQLQuery("DELETE FROM user").executeUpdate();
        transaction.commit();
        session.close();
        sessionFactory.close();
    }
}
