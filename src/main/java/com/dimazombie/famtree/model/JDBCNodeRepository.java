package com.dimazombie.famtree.model;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.naming.InitialContext;
import javax.naming.NamingException;
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
            ResultSet rs = conn.prepareStatement("select n.id, n.parentId, n.name, n.bio, n.dateOfBirth from Node n " +
                    "where n.parentId is null").executeQuery();
            while (rs.next()) {
                Long nodeId = rs.getLong("id");

                Node node = new Node(nodeId, rs.getLong("parentId"),
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
    public Node getNodeById(String nodeId) {
        Connection conn = null;
        try {
            Node node = null;

            InitialContext ctx = new InitialContext();
            DataSource ds = (DataSource) ctx.lookup("jdbc/ds");
            conn = ds.getConnection();
            ResultSet rs = conn.prepareStatement("select n.id, n.parentId, n.name, n.bio, n.dateOfBirth from Node n " +
                    "where n.id = "+ nodeId).executeQuery();
            while (rs.next()) {
                node = new Node(rs.getLong("id"),
                        rs.getLong("parentId"),
                        rs.getString("name"),
                        rs.getString("bio"),
                        rs.getString("dateOfBirth")
                );
            }
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
        ResultSet rs = conn.prepareStatement("select n.id, n.parentId, n.name, n.bio, n.dateOfBirth from Node n " +
                "where n.parentId = "+ parentId).executeQuery();
        while (rs.next()) {
            Long nodeId = rs.getLong("id");

            Node node = new Node(rs.getLong("id"),
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
        return nodes;
    }

    public Node addNewNodes(Node node) {
        Connection conn = null;
        try {
            InitialContext ctx = new InitialContext();
            DataSource ds = (DataSource) ctx.lookup("jdbc/ds");
            conn = ds.getConnection();
            PreparedStatement stmt = conn.prepareStatement("insert into Node(parentId, name, bio, dateOfBirth) " +
                    "values (?, ?, ?, ?)");
            if(node.getParentId() != null) {
                stmt.setLong(1, node.getParentId());
            } else {
                stmt.setNull(1, Types.BIGINT);
            }
            stmt.setString(2, node.getName());
            stmt.setString(3, node.getBio());
            stmt.setString(4, node.getDateOfBirth());
            stmt.execute();
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

    public void removeNode(Node node) {
        Connection conn = null;
        try {
            InitialContext ctx = new InitialContext();
            DataSource ds = (DataSource) ctx.lookup("jdbc/ds");
            conn = ds.getConnection();
            PreparedStatement stmt = conn.prepareStatement("delete from Node where id = ?");
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
