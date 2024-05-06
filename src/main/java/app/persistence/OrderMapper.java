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
                int status = rs.getInt("status");
                String shippingAddress = rs.getString("shipping_address");
                orderList.add(new Order(order_id, price, width, length, roof, shippingAddress, status));
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


}
