package com.dimazombie.famtree.model;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.persistence.Id;
import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class JDBCNodeRepository implements NodeRepository {
    private Logger logger = LoggerFactory.getLogger(NodeRepository.class);

    public List<Node> getAllNodes() {
        Connection conn = null;
        try {
            List<Node> nodes = new ArrayList<Node>();

            InitialContext ctx = new InitialContext();
            DataSource ds = (DataSource) ctx.lookup("jdbc/ds");
            conn = ds.getConnection();

            String sql = "select n.id, n.parentId, n.name, n.bio, n.dateOfBirth from Node n where n.parentId is null";
            logger.debug("SQL: " + sql);

            PreparedStatement stmt = conn.prepareStatement(sql);

            ResultSet rs = stmt.executeQuery();
            while(rs.next()) {
                Long nodeId = rs.getLong("id");

                Node node = new Node(
                        nodeId,
                        rs.getLong("parentId"),
                        rs.getString("name"),
                        rs.getString("bio"),
                        rs.getString("dateOfBirth")
                );

                List<Node> ancestors = getAncestors(nodeId, conn);
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
    public Node getById(String nodeId) {
        Connection conn = null;
        try {
            Node node = null;

            InitialContext ctx = new InitialContext();
            DataSource ds = (DataSource) ctx.lookup("jdbc/ds");
            conn = ds.getConnection();

            String sql = "select n.id, n.parentId, n.name, n.bio, n.dateOfBirth from Node n where n.id = ?";
            logger.debug("SQL: " + sql);
            logger.debug("with bind var: " + nodeId);

            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, nodeId);

            ResultSet rs = stmt.executeQuery();
            if(rs.next()) {
                node = new Node(
                        rs.getLong("id"),
                        rs.getLong("parentId"),
                        rs.getString("name"),
                        rs.getString("bio"),
                        rs.getString("dateOfBirth")
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

    private List<Node> getAncestors(Long parentId, Connection conn) throws SQLException {
        List<Node> nodes = new ArrayList<Node>();

        String sql = "select n.id, n.parentId, n.name, n.bio, n.dateOfBirth from Node n where n.parentId = ?";
        logger.debug("SQL: " + sql);
        logger.debug("with bind var: " + parentId);

        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.setLong(1, parentId);

        ResultSet rs = stmt.executeQuery();
        while (rs.next()) {
            Long nodeId = rs.getLong("id");

            Node node = new Node(
                    rs.getLong("id"),
                    rs.getLong("parentId"),
                    rs.getString("name"),
                    rs.getString("bio"),
                    rs.getString("dateOfBirth")
            );

            List<Node> ancestors = getAncestors(nodeId, conn);
            if(ancestors.size() > 0) {
                node.ancestors = ancestors;
            }

            nodes.add(node);
        }
        logger.debug("rs: " + nodes);
        return nodes;
    }

    public Node persist(Node node) {
        Connection conn = null;
        try {
            InitialContext ctx = new InitialContext();
            DataSource ds = (DataSource) ctx.lookup("jdbc/ds");
            conn = ds.getConnection();

            String sql = "insert into Node(parentId, name, bio, dateOfBirth) values (?, ?, ?, ?)";
            logger.debug("SQL: " + sql);
            logger.debug("with bind var: " + node.getParentId());
            logger.debug("with bind var: " + node.getName());
            logger.debug("with bind var: " + node.getBio());
            logger.debug("with bind var: " + node.getDateOfBirth());

            PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            if(node.getParentId() != null) {
                stmt.setLong(1, node.getParentId());
            } else {
                stmt.setNull(1, Types.BIGINT);
            }
            stmt.setString(2, node.getName());
            stmt.setString(3, node.getBio());
            stmt.setString(4, node.getDateOfBirth());
            stmt.execute();

            ResultSet rs = stmt.getGeneratedKeys();
            if(rs.next()) {
                Long id = rs.getLong(1);
                node.setId(id);
            }
            logger.debug("rs: " + node);
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
        Connection conn = null;
        try {
            InitialContext ctx = new InitialContext();
            DataSource ds = (DataSource) ctx.lookup("jdbc/ds");
            conn = ds.getConnection();

            String sql = "delete from Node where id = ?";
            logger.debug("SQL: " + sql);
            logger.debug("with bind var: " + node.getId());

            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setLong(1, node.getId());
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
