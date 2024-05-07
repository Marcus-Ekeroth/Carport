package app.persistence;

import app.entities.User;
import app.exceptions.DatabaseException;
import app.entities.Order;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class OrderMapper {


    public static List<Order> getAllOrders(ConnectionPool connectionPool) throws DatabaseException {
        List<Order> orderList = new ArrayList<>();
        String sql = "SELECT o.*, s.status FROM \"order\" o LEFT JOIN status s ON o.status_id = s.status_id;";
        try (
                Connection connection = connectionPool.getConnection();
                PreparedStatement ps = connection.prepareStatement(sql)
        ) {

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                int orderId = rs.getInt("order_id");
                double price = rs.getDouble("price");
                int width = rs.getInt("width");
                int length = rs.getInt("length");
                String roof = rs.getString("roof");
                String shippingAddress = rs.getString("shipping_address");
                int userId=rs.getInt("user_id");
                String status = rs.getString("status");
                orderList.add(new Order(orderId, price, width, length, roof, shippingAddress, userId, status));
            }
        } catch (SQLException e) {
            throw new DatabaseException("Database fejl", e.getMessage());
        }
        return orderList;
    }

    public static void createOrder(User user, int width, int length, String roof, String shippingAddress, ConnectionPool connectionPool) throws DatabaseException {

        String sql = "INSERT INTO public.\"order\" (width, length, roof, shipping_address, user_id, status_id, price) VALUES (?,?,?,?,?,?,?)";



        try (
                Connection connection = connectionPool.getConnection();
                PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)

        ) {
            ps.setInt(1, width);
            ps.setInt(2, length);
            ps.setString(3, roof);
            ps.setString(4, shippingAddress);
            ps.setInt(5, user.getUserId());
            ps.setInt(6, 1);
            ps.setDouble(7,0.0);
            int rowsAffected = ps.executeUpdate();
            if (rowsAffected != 1) {
                throw new DatabaseException("Fejl ved oprettelse af ny ordre");
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
    public static void changeStatus(int orderId,int statusId, ConnectionPool connectionPool) throws DatabaseException {
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
    public static Order getOrderById(int orderId, ConnectionPool connectionPool) {

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
    public static void updatePrice(int orderId, double price, ConnectionPool connectionPool) throws DatabaseException {
        String sql = "UPDATE public.\"order\" SET price = ? WHERE order_id = ?";

        try (
                Connection connection = connectionPool.getConnection();
                PreparedStatement ps = connection.prepareStatement(sql)
        ) {
            ps.setInt(1, orderId);
            ps.setDouble(2, price);
            int rowsAffected = ps.executeUpdate();
            if (rowsAffected != 1) {
                throw new DatabaseException("Fejl i opdatering af en balance");
            }
        } catch (SQLException e) {
            throw new DatabaseException("Fejl i opdatering af en balance", e.getMessage());
        }

    }


}
