package app.persistence;

import app.exceptions.DatabaseException;
import app.entities.Order;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class OrderMapper {


    public static List<Order> getAllOrders(ConnectionPool connectionPool) throws DatabaseException {
        List<Order> orderList = new ArrayList<>();
        String sql = "SELECT * FROM order" +
                "ORDER BY order_id ASC ";
        try (
                Connection connection = connectionPool.getConnection();
                PreparedStatement ps = connection.prepareStatement(sql)
        ) {

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                int order_id = rs.getInt("order_id");
                int price = rs.getInt("price");
                int width = rs.getInt("width");
                int length = rs.getInt("length");
                String roof = rs.getString("roof");
                Boolean status = rs.getBoolean("status");
                String shippingAddress = rs.getString("shipping_address");
                orderList.add(new Order(order_id,price,width,length,roof,status,shippingAddress));
            }
        } catch (SQLException e) {
            throw new DatabaseException("Database fejl", e.getMessage());
        }
        return orderList;
    }

    public static void createOrder(int orderId, int price, int width, int length, String roof, boolean status, String shippingAddress, ConnectionPool connectionPool) throws DatabaseException {
        String sql = "INSERT INTO order (orderId, price, width, length, roof, status, shippingAddress VALUES (?,?,?,?,?,?,?)";

        try (
                Connection connection = connectionPool.getConnection();
                PreparedStatement ps = connection.prepareStatement(sql)
        ) {
            ps.setInt(1, orderId);
            ps.setInt(2, price);
            ps.setInt(3, width);
            ps.setInt(4, length);
            ps.setString(5, roof);
            ps.setBoolean(6, status);
            ps.setString(7, shippingAddress);
            int rowsAffected = ps.executeUpdate();
            if (rowsAffected != 1) {
                throw new DatabaseException("Fejl ved oprettelse af ny order");
            }
        } catch (SQLException e) {
            String msg = "Der er sket en fejl. Prøv igen";
            throw new DatabaseException(msg, e.getMessage());
        }
    }
    /*
    public static Order approveOrder(int statusId, ConnectionPool connectionPool) throws DatabaseException {
        String sql = "UPDATE order SET status_id = ?" +
                "WHERE order_id = ? ";
        try (
                Connection connection = connectionPool.getConnection();
                PreparedStatement ps = connection.prepareStatement(sql)

        ){
            ps.setInt(7,statusId);
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                int orderId = rs.getInt("order_id");
                int price = rs.getInt("price");
                int width = rs.getInt("width");
                int length = rs.getInt("length");
                String roof = rs.getString("roof");
                String shippingAddress = rs.getString("shipping_address");
                Order order = new Order(orderId, price,width,length,roof,shippingAddress,statusId);
                return order;
            }
        } catch (SQLException e) {
            String msg = "Der er sket en fejl. Prøv igen";
            throw new DatabaseException(msg, e.getMessage());
        }


    }
    */
    public static void approveOrder(int orderId,int statusId, ConnectionPool connectionPool) throws DatabaseException {
        String sql = "UPDATE order SET status_id = ?" +
                "WHERE order_id = ? ";
        try (
                Connection connection = connectionPool.getConnection();
                PreparedStatement ps = connection.prepareStatement(sql)
        ) {
            ps.setInt(7, statusId);
            ps.setInt(1, orderId);
            int rowsAffected = ps.executeUpdate();
            if (rowsAffected != 1) {
                throw new DatabaseException("Fejl i godkendelse af bestilling");
            }
        } catch (SQLException e) {
            throw new DatabaseException("Fejl i godkendelse af bestilling", e.getMessage());
        }
    }


}
