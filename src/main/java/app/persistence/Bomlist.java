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


    public void addStolpe(int carportLength, List<Material> woodList) {
        Material stolpe = null;
        for (Material wood : woodList) {
            if (wood.getMaterialId() == 6) {
                stolpe = wood;
            }
        }

        int maxStolpeDis = 310;
        int extraSpaceFront = 100;
        int extraSpaceBehind = 30;

        int amount = 4;
        if (carportLength > (maxStolpeDis + extraSpaceFront + extraSpaceBehind)) {
            amount = 6;
        }

        addToBomlist(new Bom(stolpe, amount));

    }

    public void addRem(int carportLength, List<Material> woodList) {
        List<Material> spær = new ArrayList<>();

        for (Material wood : woodList) {
            if (wood.getMaterialId() == 5) {
                spær.add(wood);
            }
        }
        int amount480 = 0;
        int amount600 = 0;

        if (carportLength <= 240) {
            amount480 = 1;
        } else if (carportLength <= 300) {
            amount600 = 1;
        } else if (carportLength <= 480) {
            amount480 = 2;
        } else if (carportLength <= 540) {
            amount480 = 1;
            amount600 = 1;
        } else if (carportLength <= 600) {
            amount600 = 2;
        } else if (carportLength <= 720) {
            amount480 = 3;
        } else if (carportLength <= 780) {
            amount480 = 2;
            amount600 = 1;
        }


        if (spær.get(0).getLength() == 480) {

            if (amount480 > 0) {
                addToBomlist(new Bom(spær.get(0), amount480));
            }
            if (amount600 > 0) {
                addToBomlist(new Bom(spær.get(1), amount600));
            }
        } else if (spær.get(0).getLength() == 600) {

            if (amount600 > 0) {
                addToBomlist(new Bom(spær.get(0), amount600));
            }
            if (amount480 > 0) {
                addToBomlist(new Bom(spær.get(1), amount480));
            }
        }

    }

    public void addSpær(int carportLength,int carportWidth, List<Material> woodList){
        List<Material> spær = new ArrayList<>();
        for (Material wood : woodList) {
            if (wood.getMaterialId() == 5) {
                spær.add(wood);
            }
        }

        int rowAmount = 4;

        while (rowAmount*60<carportLength && rowAmount!=13){
            rowAmount++;
        }

        //Antagelse: Kun et bræde per række. Dette er for at gøre problemet lidt mere simpelt til at starte med.
        if(carportWidth<=480){
            if (spær.get(0).getLength() == 480) {
                addToBomlist(new Bom(spær.get(0), rowAmount));
            } else if (spær.get(1).getLength() == 480) {
                addToBomlist(new Bom(spær.get(1), rowAmount));
            }
        }else if(carportWidth<=600){
            if (spær.get(0).getLength() == 600) {
                addToBomlist(new Bom(spær.get(0), rowAmount));
            } else if (spær.get(1).getLength() == 600) {
                addToBomlist(new Bom(spær.get(1), rowAmount));
            }
        }


    }


}
