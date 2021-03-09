package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;

public class UserDaoHibernateImpl implements UserDao {
    private Transaction transaction = null;
    Session session = new Util().getSessionFactory().openSession();
    public UserDaoHibernateImpl() {

    }


    @Override
    public void createUsersTable() {

        transaction = session.beginTransaction();
        session.createSQLQuery("CREATE TABLE IF NOT EXISTS user (id INT PRIMARY KEY AUTO_INCREMENT, " +
                "name VARCHAR(20) NOT NULL , lastName VARCHAR(20) NOT NULL , age INT(3) NOT NULL) " +
                "DEFAULT CHARSET=utf8")
                .executeUpdate();
        transaction.commit();
        //session.close();
        System.out.println("Таблица создана.");
    }

    @Override
    public void dropUsersTable() {
        //Session session = new Util().getSessionFactory().openSession();
        transaction = session.beginTransaction();
        session.createSQLQuery("DROP TABLE IF EXISTS user").executeUpdate();
        System.out.println("Команда на удаление таблицы");
        transaction.commit();
        //session.close();
        System.out.println("Таблица удалена.");
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        User user = new User(name, lastName, age);
        //Session session = new Util().getSessionFactory().openSession();
        transaction = session.beginTransaction();
        session.save(user);
        transaction.commit();
        //session.close();
        System.out.println("User с именем " + name + " добавлен в базу данных.");
    }

    @Override
    public void removeUserById(long id) {
        //Session session = new Util().getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();
        User user = (User) session.get(User.class, id);
        session.delete(user);
        transaction.commit();
        //session.close();
        System.out.println("User с номером " + id + " удален из базы данных.");
    }

    @Override
    public List<User> getAllUsers() {
        //Session session = new Util().getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();
        List<User> l = session.createQuery("from User").list();
        //session.close();
       transaction.commit();
        System.out.println("Список составлен.");
        return l;
    }

    @Override
    public void cleanUsersTable() {
        //Session session = new Util().getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();
        session.createSQLQuery("DELETE FROM user").executeUpdate();
        transaction.commit();
        //session.close();
        System.out.println("Таблица очищена.");
    }
}
