package app.persistence;

public class Material {
    private int materialId;
    private String name;
    private String unit;
    private String description;
    private double price;
    private int length;

    public Material(int materialId, String name, String unit, String description, double price) {
        this.materialId = materialId;
        this.name = name;
        this.unit = unit;
        this.description = description;
        this.price = price;
    }
    public Material(int materialId, String name, String unit, String description, double price, int length) {
        this.materialId = materialId;
        this.name = name;
        this.unit = unit;
        this.description = description;
        this.price = price;
        this.length = length;
    }


}
