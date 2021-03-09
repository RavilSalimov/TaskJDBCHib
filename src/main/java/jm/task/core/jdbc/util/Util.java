package jm.task.core.jdbc.util;

import jm.task.core.jdbc.model.User;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;

import java.sql.Connection;
import java.sql.DriverManager;

public class Util {
    private Connection connection;
    private final String url = "jdbc:mysql://localhost/ravil?useSSL=false&serverTimezone=Europe/Moscow";
    private final String username = "root";
    private final String password = "Ravil";
    private SessionFactory sessionFactory;

    public Connection getConnection() {

        try {
            connection = DriverManager.getConnection(url, username, password);
            System.out.println("Connection to Store DB OK!");

        } catch (Exception ex) {
            System.out.println("Connection failed...");
            ex.printStackTrace();
        }
        return connection;// реализуйте настройку соеденения с БД
    }

    public SessionFactory getSessionFactory() {

        if (sessionFactory == null) {
            try {
                Configuration configuration = new Configuration()
                        .setProperty("hibernate.connection.driver_class", "com.mysql.cj.jdbc.Driver")//???
                        .setProperty("hibernate.connection.url", "jdbc:mysql://localhost:3306/ravil?serverTimezone=Europe/Moscow&useSSL=false")
                        .setProperty("hibernate.connection.username", "root")
                        .setProperty("hibernate.connection.password", "Ravil")
                        .setProperty("hibernate.dialect", "org.hibernate.dialect.MySQLDialect")
                        .setProperty("hibernate.HBM2DDL_AUTO", "create-drop")
                        .setProperty("SHOW_SQL", "true")
                        .addAnnotatedClass(User.class);
                configuration.addAnnotatedClass(User.class);
                StandardServiceRegistryBuilder builder = new StandardServiceRegistryBuilder().applySettings(configuration.getProperties());
                sessionFactory = configuration.buildSessionFactory(builder.build());
                System.out.println("Подключение Hibernate!");
            } catch (Exception e) {
                System.out.println("Connection failed Hibernate..." + e);
            }
        }
        return sessionFactory;
    }
}
// реализуйте настройку соеденения с БД

