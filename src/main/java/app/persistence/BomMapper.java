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


}
