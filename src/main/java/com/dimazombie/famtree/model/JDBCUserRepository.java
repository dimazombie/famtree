package com.dimazombie.famtree.model;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class JDBCUserRepository implements UserRepository {

    private Logger logger = LoggerFactory.getLogger(UserRepository.class);

    public User findByLoginPassword(String login, String password) {
        Connection conn = null;
        try {
            User user = null;

            InitialContext ctx = new InitialContext();
            DataSource ds = (DataSource) ctx.lookup("jdbc/ds");
            conn = ds.getConnection();

            String sql = "select t.id, t.first_name, t.last_name from USER t where t.login = ? and t.password = ?";
            logger.debug("SQL: " + sql);
            logger.debug("with bind var: " + login);
            logger.debug("with bind var: " + password);

            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, login);
            stmt.setString(2, password);

            ResultSet rs = stmt.executeQuery();
            if(rs.next()) {
                user = new User(
                        rs.getLong("id"),
                        login,
                        rs.getString("first_name"),
                        rs.getString("last_name")
                );
            }
            logger.debug("rs: " + user);
            return user;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (NamingException e) {
            throw new RuntimeException(e);
        } finally {
            closeSilently(conn);
        }
    }

    private void closeSilently(Connection conn) {
        try {
            if (conn != null) conn.close();
        }
        catch (SQLException ignore) {
        }
    }
}
