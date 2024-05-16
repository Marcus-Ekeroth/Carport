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

    public static Bomlist getBomlistById(int orderId, ConnectionPool connectionPool) throws DatabaseException {

        String sql = "SELECT b.order_id, b.amount, m.*, mv.material_variant_id, mv.length FROM bom b LEFT JOIN material m ON b.material_id = m.material_id LEFT JOIN material_variant mv ON b.material_variant_id = mv.material_variant_id WHERE b.order_id=?";

        Bomlist bomlist = new Bomlist();

        try (
                Connection connection = connectionPool.getConnection();
                PreparedStatement ps = connection.prepareStatement(sql);
        ) {
            ps.setInt(1, orderId);

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                int materialId = rs.getInt("material_id");
                String name = rs.getString("name");
                String unit = rs.getString("unit");
                String description = rs.getString("description");
                double price = rs.getDouble("price");
                int length = rs.getInt("length");
                int materialVariantId = rs.getInt("material_variant_id");
                int amount = rs.getInt("amount");

                bomlist.addToBomlist(new Bom(new Material(materialId, name, unit, description, price, length, materialVariantId), amount));

            }

        } catch (SQLException e) {
            throw new DatabaseException("Database fejl", e.getMessage());
        }
        return bomlist;
    }
}
