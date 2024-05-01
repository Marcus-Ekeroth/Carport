package app.persistence;

import app.entities.User;
import app.exceptions.DatabaseException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserMapper {

    public static User login(String email, String password, ConnectionPool connectionPool) throws DatabaseException {
        String sql = "select * from users where email=? and password=?";

        try (
                Connection connection = connectionPool.getConnection();
                PreparedStatement ps = connection.prepareStatement(sql)
        ) {
            ps.setString(1, email);
            ps.setString(2, password);

            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                int id = rs.getInt("user_id");
                String name = rs.getString("name");
                String address = rs.getString("address");
                int postalcode = rs.getInt("postalcode");
                String city = rs.getString("city");
                int phonenumber = rs.getInt("phonenumber");
                String role = rs.getString("role");

                return new User(id, name, email, password, address, postalcode, city, phonenumber, role);
            } else {
                throw new DatabaseException("Fejl i login. Prøv igen");
            }
        } catch (SQLException e) {
            throw new DatabaseException("DB fejl", e.getMessage());
        }
    }

    public static void createuser(String name, String email, String password, String address, int postalcode, String city, int phonenumber, ConnectionPool connectionPool) throws DatabaseException {
        String sql = "insert into users (name, email, password, address, postalcode, city, phonenumber,role) values (?,?,?,?,?,?,?,?)";

        try (
                Connection connection = connectionPool.getConnection();
                PreparedStatement ps = connection.prepareStatement(sql)
        ) {
            ps.setString(1, name);
            ps.setString(2, email);
            ps.setString(3, password);
            ps.setString(4, address);
            ps.setInt(5, postalcode);
            ps.setString(6, city);
            ps.setInt(7, phonenumber);
            ps.setString(8, "user");


            int rowsAffected = ps.executeUpdate();
            if (rowsAffected != 1) {
                throw new DatabaseException("Fejl ved oprettelse af ny bruger");
            }
        } catch (SQLException e) {
            String msg = "Der er sket en fejl. Prøv igen";
            if (e.getMessage().startsWith("ERROR: duplicate key value ")) {
                msg = "Denne email er allerede brugt.";
            }
            throw new DatabaseException(msg, e.getMessage());
        }
    }

    public static List<User> getAllUsers(ConnectionPool connectionPool) throws DatabaseException
    {
        List<User> userList = new ArrayList<>();
        String sql = "select * from users order by user_id";

        try (
                Connection connection = connectionPool.getConnection();
                PreparedStatement ps = connection.prepareStatement(sql)
        )
        {
            ResultSet rs = ps.executeQuery();
            while (rs.next())
            {
                int id = rs.getInt("user_id");
                String name = rs.getString("name");
                String email = rs.getString("email");
                String password = rs.getString("password");
                String address = rs.getString("address");
                int postalcode = rs.getInt("postalcode");
                String city = rs.getString("city");
                int phonenumber = rs.getInt("phonenumber");
                String role = rs.getString("role");
                userList.add(new User(id, name, email, password, address, postalcode, city, phonenumber, role));
            }
        }
        catch (SQLException e)
        {
            throw new DatabaseException("Fejl!!!!", e.getMessage());
        }
        return userList;
    }

}
