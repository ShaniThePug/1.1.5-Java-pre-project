package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBCImpl implements UserDao {


    public UserDaoJDBCImpl() {
    }

    public void createUsersTable() { //создание таблицы
        try (Statement statement = Util.getConnection().createStatement()) {
            String sql = "CREATE TABLE IF NOT EXISTS user (Id INT PRIMARY KEY AUTO_INCREMENT, " +
                    "name VARCHAR(45), lastName VARCHAR(45), age TINYINT)";
            statement.executeUpdate(sql);
            System.out.println("Таблица создана");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void dropUsersTable() { //удаление таблицы
        try (Statement statement = Util.getConnection().createStatement()) {
            String sql = "DROP TABLE IF EXISTS user";
            statement.executeUpdate(sql);
            System.out.println("Таблица удалена");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void saveUser(String name, String lastName, byte age) { //добавление User в таблицу
        String sql = "INSERT USER(name, lastName, age) VALUES(?, ?, ?)";
        try (PreparedStatement preparedStatements = Util.getConnection().prepareStatement(sql)) {
            preparedStatements.setString(1, name);
            preparedStatements.setString(2, lastName);
            preparedStatements.setInt(3, age);
            preparedStatements.executeUpdate();
            System.out.println("Пользователь " + name + " " + lastName + " успешно добавлен в таблицу");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    public void removeUserById(long id) { //удаление User из таблицы (по id)
        try (PreparedStatement preparedStatements = Util.getConnection().prepareStatement("DELETE FROM user WHERE ID = ?")) {
            preparedStatements.setLong(1, id);
            preparedStatements.executeUpdate();
            System.out.println("Пользователь с id:" + id + " удалён из таблицы");
        } catch (SQLException e) {
            throw new RuntimeException();
        }
    }

    public List<User> getAllUsers() { //получение всех User(ов) из таблицы
        List<User> allUsers = new ArrayList<>();
        try (Statement statement = Util.getConnection().createStatement()) {
            ResultSet resultSet = statement.executeQuery("SELECT * FROM user");
            while (resultSet.next()) {
                String name = resultSet.getString(2);
                String lastName = resultSet.getString(3);
                byte age = resultSet.getByte(4);
                User user = new User(name, lastName, age);
                allUsers.add(user);
            }
        } catch (SQLException e) {
            throw new RuntimeException();
        }
        return allUsers;
    }

    public void cleanUsersTable() { //очистка содержания таблицы
        String sql = "TRUNCATE TABLE user";
        try (Statement statement = Util.getConnection().createStatement()) {
            statement.executeUpdate(sql);
            System.out.println("Все пользователи из таблицы удалены");
        } catch (SQLException e) {
            throw new RuntimeException();
        }
    }
}
