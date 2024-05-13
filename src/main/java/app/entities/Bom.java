package app.entities;

public class Bom {
    private Material material;
    private int amount;

    public Bom(Material material, int amount) {
        this.material = material;
        this.amount = amount;
    }

    public int getAmount() {
        return amount;
    }

    public Material getMaterial() {
        return material;
    }
}
