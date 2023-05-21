package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;

import jm.task.core.jdbc.util.Util;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.ArrayList;
import java.util.List;

public class UserDaoHibernateImpl implements UserDao {
    Transaction transaction;

    public UserDaoHibernateImpl() {

    }

    @Override
    public void createUsersTable() { //создание таблицы
        try (Session session = Util.getSessionFactory().openSession()) {
            String sql = "CREATE TABLE IF NOT EXISTS hibernateUser (Id INT NOT NULL AUTO_INCREMENT PRIMARY KEY, " +
                    "name VARCHAR(45), lastName VARCHAR(45), age INT)";
            transaction = session.beginTransaction();
            session.createSQLQuery(sql).executeUpdate();
            transaction.commit();
            System.out.println("Таблица создана");

        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback(); //в случае ошибки, откатываем транзакцию
            }
        }
    }

    @Override
    public void dropUsersTable() { //удаление таблицы
        try (Session session = Util.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            String sql = "DROP TABLE IF EXISTS hibernateUser";
            session.createSQLQuery(sql).executeUpdate();
            transaction.commit();
            System.out.println("Таблица удалена");
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
        }
    }

    @Override
    public void saveUser(String name, String lastName, byte age) { //добавление User в таблицу
        try (Session session = Util.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            User user = new User(name, lastName, age);
            session.persist(user); //persist = save
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
        }
    }

    @Override
    public void removeUserById(long id) { //удаление User из таблицы (по id)
        try (Session session = Util.getSessionFactory().openSession()) {
            User user = session.get(User.class, id);
            transaction = session.beginTransaction();
            session.delete(user);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
        }
    }


    @Override
    public List<User> getAllUsers() { //получение всех User(ов) из таблицы
        List<User> allUsers = new ArrayList<>();
        try (Session session = Util.getSessionFactory().openSession()) {
            session.beginTransaction();
            allUsers = session.createQuery("FROM User").list();
            session.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return allUsers;
    }

    @Override
    public void cleanUsersTable() { //очистка содержания таблицы
        try (Session session = Util.getSessionFactory().openSession()) {
            String sql = "TRUNCATE TABLE hibernateUser";
            transaction = session.beginTransaction();
            session.createNativeQuery(sql).executeUpdate();
            transaction.commit();
            System.out.println("Таблица очищена");
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
        }
    }
}
