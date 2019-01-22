package com.dimazombie.famtree.model;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.core.Context;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class JDBCNodeRepository implements NodeRepository {
    private Logger logger = LoggerFactory.getLogger(NodeRepository.class);

    @Context ContainerRequestContext context;

    public List<Node> getAllNodes() {
        User user = (User) context.getProperty(User.PROPERTY_NAME);
        Connection conn = null;
        try {
            List<Node> nodes = new ArrayList<Node>();

            InitialContext ctx = new InitialContext();
            DataSource ds = (DataSource) ctx.lookup("jdbc/ds");
            conn = ds.getConnection();

            String sql = "select t.id, t.parent_id, t.name, t.bio, t.image_id, t.date_of_birth " +
                    "from NODE t " +
                    "where t.parent_id is null and t.user_id = ? " +
                    "order by t.created_date";
            logger.debug("SQL: " + sql);
            logger.debug("with bind var: " + user.getId());

            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setLong(1, user.getId());

            ResultSet rs = stmt.executeQuery();
            while(rs.next()) {
                Long id = rs.getLong("id");
                Long parentId = rs.getLong("parent_id");
                if (rs.wasNull()) parentId = null;
                Long imageId = rs.getLong("image_id");
                if (rs.wasNull()) imageId = null;

                Node node = new Node(
                        id,
                        parentId,
                        rs.getString("name"),
                        rs.getString("bio"),
                        imageId,
                        rs.getString("date_of_birth")
                );

                List<Node> ancestors = getAncestors(id, conn);
                if(ancestors.size() > 0) {
                    node.ancestors = ancestors;
                }

                nodes.add(node);
            }
            logger.debug("rs: " + nodes);
            return nodes;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (NamingException e) {
            throw new RuntimeException(e);
        } finally {
            closeSilently(conn);
        }
    }

    @Override
    public Node findById(String nodeId) {
        User user = (User) context.getProperty(User.PROPERTY_NAME);
        Connection conn = null;
        try {
            Node node = null;

            InitialContext ctx = new InitialContext();
            DataSource ds = (DataSource) ctx.lookup("jdbc/ds");
            conn = ds.getConnection();

            String sql = "select t.id, t.parent_id, t.name, t.bio, t.image_id, t.date_of_birth " +
                    "from NODE t " +
                    "where t.id = ? and t.user_id = ?";
            logger.debug("SQL: " + sql);
            logger.debug("with bind var: " + nodeId);
            logger.debug("with bind var: " + user.getId());

            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, nodeId);
            stmt.setLong(2, user.getId());

            ResultSet rs = stmt.executeQuery();
            if(rs.next()) {
                Long parentId = rs.getLong("parent_id");
                if (rs.wasNull()) parentId = null;
                Long imageId = rs.getLong("image_id");
                if (rs.wasNull()) imageId = null;

                node = new Node(
                        rs.getLong("id"),
                        parentId,
                        rs.getString("name"),
                        rs.getString("bio"),
                        imageId,
                        rs.getString("date_of_birth")
                );
            }
            logger.debug("rs: " + node);
            return node;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (NamingException e) {
            throw new RuntimeException(e);
        } finally {
            closeSilently(conn);
        }
    }

    private List<Node> getAncestors(Long nodeId, Connection conn) throws SQLException {
        User user = (User) context.getProperty(User.PROPERTY_NAME);
        List<Node> nodes = new ArrayList<Node>();

        String sql = "select t.id, t.parent_id, t.name, t.bio, t.image_id, t.date_of_birth " +
                "from NODE t " +
                "where t.parent_id = ? and t.user_id = ?" +
                "order by t.created_date";
        logger.debug("SQL: " + sql);
        logger.debug("with bind var: " + nodeId);
        logger.debug("with bind var: " + user.getId());

        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.setLong(1, nodeId);
        stmt.setLong(2, user.getId());

        ResultSet rs = stmt.executeQuery();
        while (rs.next()) {
            Long id = rs.getLong("id");
            Long parentId = rs.getLong("parent_id");
            if (rs.wasNull()) parentId = null;
            Long imageId = rs.getLong("image_id");
            if (rs.wasNull()) imageId = null;

            Node node = new Node(
                    id,
                    parentId,
                    rs.getString("name"),
                    rs.getString("bio"),
                    imageId,
                    rs.getString("date_of_birth")
            );

            List<Node> ancestors = getAncestors(id, conn);
            if(ancestors.size() > 0) {
                node.ancestors = ancestors;
            }

            nodes.add(node);
        }
        logger.debug("rs: " + nodes);
        return nodes;
    }

    public Node persist(Node node) {
        User user = (User) context.getProperty(User.PROPERTY_NAME);
        Connection conn = null;
        try {
            InitialContext ctx = new InitialContext();
            DataSource ds = (DataSource) ctx.lookup("jdbc/ds");
            conn = ds.getConnection();

            if(node.getId() == null) {
                String sql = "insert into NODE(parent_id, name, bio, image_id, date_of_birth, user_id, created_date) values (?, ?, ?, ?, ?, ?, sysdate)";
                logger.debug("SQL: " + sql);
                logger.debug("with bind var: " + node.getParentId());
                logger.debug("with bind var: " + node.getName());
                logger.debug("with bind var: " + node.getBio());
                logger.debug("with bind var: " + node.getImageId());
                logger.debug("with bind var: " + node.getDateOfBirth());
                logger.debug("with bind var: " + user.getId());

                PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
                if (node.getParentId() != null) {
                    stmt.setLong(1, node.getParentId());
                } else {
                    stmt.setNull(1, Types.BIGINT);
                }
                stmt.setString(2, node.getName());
                stmt.setString(3, node.getBio());
                if (node.getImageId() != null) {
                    stmt.setLong(4, node.getImageId());
                } else {
                    stmt.setNull(4, Types.BIGINT);
                }
                stmt.setString(5, node.getDateOfBirth());
                stmt.setLong(6, user.getId());
                stmt.execute();

                ResultSet rs = stmt.getGeneratedKeys();
                if(rs.next()) {
                    Long id = rs.getLong(1);
                    node.setId(id);
                }
                logger.debug("rs: " + node);
            } else {
                String sql = "update NODE t set t.parent_id = ?, t.name = ?, t.bio = ?, t.image_id = ?, " +
                        "t.date_of_birth = ?, t.modified_date = sysdate " +
                        "where t.id = ? and t.user_id = ?";
                logger.debug("SQL: " + sql);
                logger.debug("with bind var: " + node.getParentId());
                logger.debug("with bind var: " + node.getName());
                logger.debug("with bind var: " + node.getBio());
                logger.debug("with bind var: " + node.getImageId());
                logger.debug("with bind var: " + node.getDateOfBirth());
                logger.debug("with bind var: " + node.getId());
                logger.debug("with bind var: " + user.getId());

                PreparedStatement stmt = conn.prepareStatement(sql);
                if (node.getParentId() != null) {
                    stmt.setLong(1, node.getParentId());
                } else {
                    stmt.setNull(1, Types.BIGINT);
                }
                stmt.setString(2, node.getName());
                stmt.setString(3, node.getBio());
                if (node.getImageId() != null) {
                    stmt.setLong(4, node.getImageId());
                } else {
                    stmt.setNull(4, Types.BIGINT);
                }
                stmt.setString(5, node.getDateOfBirth());
                stmt.setLong(6, node.getId());
                stmt.setLong(7, user.getId());
                stmt.execute();
            }

            return node;
        }
        catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (NamingException e) {
            throw new RuntimeException(e);
        } finally {
            closeSilently(conn);
        }
    }

    public void remove(Node node) {
        User user = (User) context.getProperty(User.PROPERTY_NAME);
        Connection conn = null;
        try {
            InitialContext ctx = new InitialContext();
            DataSource ds = (DataSource) ctx.lookup("jdbc/ds");
            conn = ds.getConnection();

            String sql = "delete from NODE t where t.id = ? and t.user_id = ?";
            logger.debug("SQL: " + sql);
            logger.debug("with bind var: " + node.getId());
            logger.debug("with bind var: " + user.getId());

            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setLong(1, node.getId());
            stmt.setLong(2, user.getId());
            stmt.execute();
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
