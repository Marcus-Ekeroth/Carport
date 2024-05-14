package app.persistence;

import app.entities.*;
import app.exceptions.DatabaseException;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class BomMapper {

    public static void createBom(int orderId, int materialId, int materialVariantId, int amount, ConnectionPool connectionPool) throws DatabaseException {
        String sql = "insert into bom (order_id, material_id,  material_variant_id, amount) values (?,?,?,?)";

        try (
                Connection connection = connectionPool.getConnection();
                PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)
        ) {
            ps.setInt(1, orderId);
            ps.setInt(2, materialId);
            ps.setInt(3, materialVariantId);
            ps.setInt(4, amount);

            int rowsAffected = ps.executeUpdate();
            if (rowsAffected != 1) {
                throw new DatabaseException("Fejl ved oprettelse af ny ordre");
            }
        } catch (SQLException e) {
            String msg = "Der er sket en fejl. Prøv igen";
            throw new DatabaseException(msg, e.getMessage());
        }
    }



    public static List<Order> getUserOrder(User user,ConnectionPool connectionPool) throws DatabaseException {
        List<Order> orderUserList = new ArrayList<>();
        String sql = "SELECT o.*, s.status FROM \"order\" o LEFT JOIN status s ON o.status_id = s.status_id WHERE o.user_id = ?;";
        try (
                Connection connection = connectionPool.getConnection();
                PreparedStatement ps = connection.prepareStatement(sql)
        ) {
            ps.setInt(1,user.getUserId());

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                int orderId = rs.getInt("order_id");
                double price = rs.getDouble("price");
                int width = rs.getInt("width");
                int length = rs.getInt("length");
                String roof = rs.getString("roof");
                String shippingAddress = rs.getString("shipping_address");
                int userId= rs.getInt(user.getUserId());
                String status = rs.getString("status");
                orderUserList.add(new Order(orderId, price, width, length, roof, shippingAddress, userId, status));
            }
        } catch (SQLException e) {
            throw new DatabaseException("Database fejl", e.getMessage());
        }
        return orderUserList;
    }
    public static Order getBomlistById(int orderId, ConnectionPool connectionPool) {

        String sql = "SELECT o.*, s.status FROM \"order\" o LEFT JOIN status s ON o.status_id = s.status_id WHERE order_id=?;";
        Order order;

        try (
                Connection connection = connectionPool.getConnection();
                PreparedStatement ps = connection.prepareStatement(sql);
        ) {
            ps.setInt(1, orderId);

            ResultSet rs = ps.executeQuery();
            rs.next(); {
                int id = rs.getInt("order_id");
                double price = rs.getDouble("price");
                int width = rs.getInt("width");
                int length = rs.getInt("length");
                String roof = rs.getString("roof");
                String shippingAddress = rs.getString("shipping_address");
                int userId = rs.getInt("user_id");
                String status = rs.getString("status");

                order = new Order(id, price, width, length, roof, shippingAddress, userId, status);

            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return order;
    }


}
