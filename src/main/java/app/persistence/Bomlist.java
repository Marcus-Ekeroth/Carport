package app.persistence;

import app.entities.Bom;
import java.util.ArrayList;
import java.util.List;

public class Bomlist {
    private List<Bom> bomlist = new ArrayList<>();

    public void addToBomlist(Bom bom) {
        bomlist.add(bom);

    }

    public List<Bom> getOrderLines() {
        return bomlist;
    }
}
