package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBCImpl implements UserDao {
    private PreparedStatement preparedStatement;

    public UserDaoJDBCImpl() {

    }

    public void createUsersTable() {
        try (Connection connection = new Util().getConnection()) {
            String str = "CREATE TABLE IF NOT EXISTS user (Id INT PRIMARY KEY AUTO_INCREMENT," +
                    " Name VARCHAR(20) NOT NULL , lastName VARCHAR(20) NOT NULL , " +
                    "Age INT(3) NOT NULL );";
            Statement statement = connection.createStatement();
            statement.executeUpdate(str);
            statement.close();
            System.out.println("Таблица создана, либо уже существовала.");
        } catch (Exception ex) {
            System.out.println("Таблица не создана.");
            ex.printStackTrace();
        }
    }

    public void dropUsersTable() {
        try (Connection connection = new Util().getConnection()) {
            Statement statement = connection.createStatement();
            statement.executeUpdate("DROP TABLE IF EXISTS user");
            System.out.println("Таблица удалена, либо уже не существовала.");
            statement.close();
        } catch (Exception ex) {
            System.out.println("Таблица не удалена.");
        }
    }

    public void saveUser(String name, String lastName, byte age) {
        try (Connection conn = new Util().getConnection()) {
            String sql = "INSERT INTO user (name, lastname, age) VALUES (?,?,?)";
            preparedStatement = conn.prepareStatement(sql);//execute????????
            preparedStatement.setString(1, name);
            preparedStatement.setString(2, lastName);
            preparedStatement.setInt(3, age);
            preparedStatement.executeUpdate();
            System.out.println("Пользователь добавлен.");
            preparedStatement.close();
        } catch (Exception ex) {
            System.out.println("Пользователь не добавлен.");
        }
    }

    public void removeUserById(long id) {
        try (Connection conn = new Util().getConnection()) {
            String sql = "DELETE FROM user WHERE ID  = " + id;
            preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setLong(1, id);
            preparedStatement.executeUpdate();
            System.out.println("Удален.");
        } catch (Exception ex) {
            System.out.println("Не удален.");
        }
    }

    public List<User> getAllUsers() {
        List<User> userList = new ArrayList<>();
        String sql = "SELECT * FROM user";

        try (Connection conn = new Util().getConnection()) {
            Statement statement = conn.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);

            while (resultSet.next()) {
                userList.add(new User(
                        resultSet.getString(2),
                        resultSet.getString(3),
                        (byte) resultSet.getInt(4)));
            }
            statement.close();
            System.out.println("Список пользователей составлен");
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Список пользователей не составлен");
        }
        return userList;
    }

    public void cleanUsersTable() {
        try (Connection conn = new Util().getConnection()) {
            String sql = "DELETE FROM user";
            Statement statement = conn.createStatement();
            statement.executeUpdate(sql);
            System.out.println("Таблица очищена.");
        } catch (Exception ex) {
            System.out.println("Таблица не очищена.");
        }
    }
}
