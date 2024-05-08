package app.entities;

import app.persistence.Material;

public class Bom {
    Material material;
    int amount;

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
