package com.dimazombie.famtree.model;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import java.sql.*;

public class JDBCFileEntryRepository implements FileEntryRepository {
    private Logger logger = LoggerFactory.getLogger(FileEntryRepository.class);

    @Override
    public FileEntry getById(String fileEntryId) {
        Connection conn = null;
        try {
            FileEntry fileEntry = null;

            InitialContext ctx = new InitialContext();
            DataSource ds = (DataSource) ctx.lookup("jdbc/ds");
            conn = ds.getConnection();

            String sql = "select t.id, t.name from FILE_ENTRY t where t.id = ?";
            logger.debug("SQL: " + sql);
            logger.debug("with bind var: " + fileEntryId);

            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, fileEntryId);

            ResultSet rs = stmt.executeQuery();
            if(rs.next()) {
                fileEntry = new FileEntry(
                        rs.getLong("id"),
                        rs.getString("name")
                );
            }
            logger.debug("rs: " + fileEntry);
            return fileEntry;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (NamingException e) {
            throw new RuntimeException(e);
        } finally {
            closeSilently(conn);
        }
    }

    @Override
    public FileEntry persist(FileEntry fileEntry) {
        Connection conn = null;
        try {
            InitialContext ctx = new InitialContext();
            DataSource ds = (DataSource) ctx.lookup("jdbc/ds");
            conn = ds.getConnection();

            String sql = "insert into FILE_ENTRY(name) values (?)";
            logger.debug("SQL: " + sql);
            logger.debug("with bind var: " + fileEntry.getName());

            PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            stmt.setString(1, fileEntry.getName());
            stmt.execute();

            ResultSet rs = stmt.getGeneratedKeys();
            if(rs.next()) {
                Long id = rs.getLong(1);
                fileEntry.setId(id);
            }
            logger.debug("rs: " + fileEntry);
            return fileEntry;
        }
        catch (SQLException e) {
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
