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
    public static int createOrder(User user, int width, int length, String roof, String shippingAddress, ConnectionPool connectionPool, double price) throws DatabaseException {
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
            ps.setDouble(7, price);

            int rowsAffected = ps.executeUpdate();
            if (rowsAffected != 1) {
                throw new DatabaseException("Fejl ved oprettelse af ny ordre");
            }

            try (ResultSet generatedKeys = ps.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    return generatedKeys.getInt(1);
                } else {
                    throw new DatabaseException("Fejl ved hentning af ordre ID");
                }
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
    public static void changeStatus(int orderId,int statusId, ConnectionPool connectionPool) throws DatabaseException {
        String sql = "UPDATE public.\"order\" SET status_id = ? WHERE order_id = ?";
        try (
                Connection connection = connectionPool.getConnection();
                PreparedStatement ps = connection.prepareStatement(sql)
        ) {
            ps.setInt(1, statusId);
            ps.setInt(2, orderId);
            int rowsAffected = ps.executeUpdate();
            if (rowsAffected != 1) {
                throw new DatabaseException("Fejl i opdatering af status");
            }
        } catch (SQLException e) {
            throw new DatabaseException("Fejl i opdatering af status", e.getMessage());
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

    public static void deleteOrderById(int orderId, ConnectionPool connectionPool) throws DatabaseException {

        String sql = "DELETE FROM \"order\" WHERE order_id=?;";
        try (
                Connection connection = connectionPool.getConnection();
                PreparedStatement ps = connection.prepareStatement(sql);
        ) {
            ps.setInt(1, orderId);
            int rowsAffected = ps.executeUpdate();
            if(rowsAffected != 1){
                throw new DatabaseException("Fejl i sletning af order");
            }

        } catch (SQLException e) {
            throw new DatabaseException("Fejl i sletning af order", e.getMessage());
        }
    }

    public static void updatePrice(int orderId, double price, ConnectionPool connectionPool) throws DatabaseException {
        String sql = "UPDATE public.\"order\" SET price = ? WHERE order_id = ?";

        try (
                Connection connection = connectionPool.getConnection();
                PreparedStatement ps = connection.prepareStatement(sql)
        ) {
            ps.setDouble(1, price);
            ps.setInt(2, orderId);

            int rowsAffected = ps.executeUpdate();
            if (rowsAffected != 1) {
                throw new DatabaseException("Fejl i opdatering af prisen");
            }
        } catch (SQLException e) {
            throw new DatabaseException("Fejl i opdatering af en pris", e.getMessage());
        }

    }


}
