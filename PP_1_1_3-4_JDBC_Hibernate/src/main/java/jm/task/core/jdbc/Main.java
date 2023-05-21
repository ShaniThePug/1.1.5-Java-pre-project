package jm.task.core.jdbc;

import jm.task.core.jdbc.dao.UserDao;
import jm.task.core.jdbc.dao.UserDaoHibernateImpl;
import jm.task.core.jdbc.model.User;


public class Main {
    public static void main(String[] args) {
        UserDao userDaoHibernate = new UserDaoHibernateImpl();

        userDaoHibernate.createUsersTable();

        userDaoHibernate.saveUser("Daniel", "Radcliffe", (byte) 33);
        userDaoHibernate.saveUser("Timothee", "Chalamet", (byte) 27);
        userDaoHibernate.saveUser("Chris", "Pratt", (byte) 43);
        userDaoHibernate.saveUser("Chris", "Evans", (byte) 41);

        userDaoHibernate.removeUserById(3);

        for (User user : userDaoHibernate.getAllUsers()) {
            System.out.println(user);
        }

        userDaoHibernate.cleanUsersTable();
        userDaoHibernate.dropUsersTable();

    }
}
