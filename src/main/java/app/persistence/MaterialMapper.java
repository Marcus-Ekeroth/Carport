package app.persistence;

import app.entities.Material;
import app.exceptions.DatabaseException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MaterialMapper {
    public static List<Material> getAllWood(ConnectionPool connectionPool) throws DatabaseException {
        List<Material> materialList = new ArrayList<>();
        String sql = "SELECT material.material_id, material.name, material.unit, material.description, material.price, material_variant.length FROM material INNER JOIN material_variant ON material.material_id = material_variant.material_id\n" +
                "ORDER BY material.material_id ASC ";
        try (
                Connection connection = connectionPool.getConnection();
                PreparedStatement ps = connection.prepareStatement(sql)
        ) {

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                int materialId = rs.getInt("material_id");
                String name = rs.getString("name");
                String unit = rs.getString("unit");
                String description = rs.getString("description");
                int price = rs.getInt("price");
                int length = rs.getInt("length");
                materialList.add(new Material(materialId, name, unit, description, price, length));
            }
        } catch (SQLException e) {
            throw new DatabaseException("Database fejl", e.getMessage());
        }
        return materialList;
    }


}
