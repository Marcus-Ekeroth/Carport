package app.persistence;

import app.entities.Material;
import app.exceptions.DatabaseException;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class BomlistTest {
    private static final String USER = "postgres";
    private static final String PASSWORD = "postgres";
    private static final String URL = "jdbc:postgresql://localhost:5432/%s?currentSchema=public";
    private static final String DB = "carport";

    private static final ConnectionPool connectionPool = ConnectionPool.getInstance(USER, PASSWORD, URL, DB);
    List<Material> woodList = MaterialMapper.getAllWood(connectionPool);

    BomlistTest() throws DatabaseException {
    }


    @Test
    void addPoles() {
        Bomlist bomlist = new Bomlist();
        bomlist.addPoles(440, woodList);
        bomlist.addPoles(441, woodList);

        assertEquals(4,bomlist.getOrderLines().get(0).getAmount());
        assertEquals(6,bomlist.getOrderLines().get(1).getAmount());
    }

    @Test
    void addBeams() {
        Bomlist bomlist = new Bomlist();
        //test 1
        bomlist.addBeams(240, woodList);
        //test 2
        bomlist.addBeams(300, woodList);
        //test 3
        bomlist.addBeams(480, woodList);
        //test 4
        bomlist.addBeams(540, woodList);
        //test 5
        bomlist.addBeams(600, woodList);
        //test 6
        bomlist.addBeams(720, woodList);
        //test 7
        bomlist.addBeams(780, woodList);

        //test 1
        assertEquals(1,bomlist.getOrderLines().get(0).getAmount());
        assertEquals(480,bomlist.getOrderLines().get(0).getMaterial().getLength());
        //test 2
        assertEquals(1,bomlist.getOrderLines().get(1).getAmount());
        assertEquals(600,bomlist.getOrderLines().get(1).getMaterial().getLength());
        //test 3
        assertEquals(2,bomlist.getOrderLines().get(2).getAmount());
        assertEquals(480,bomlist.getOrderLines().get(2).getMaterial().getLength());
        //test 4
        assertEquals(1,bomlist.getOrderLines().get(3).getAmount());
        assertEquals(600,bomlist.getOrderLines().get(3).getMaterial().getLength());
        assertEquals(1,bomlist.getOrderLines().get(4).getAmount());
        assertEquals(480,bomlist.getOrderLines().get(4).getMaterial().getLength());
        //test 5
        assertEquals(2,bomlist.getOrderLines().get(5).getAmount());
        assertEquals(600,bomlist.getOrderLines().get(5).getMaterial().getLength());
        //test 6
        assertEquals(3,bomlist.getOrderLines().get(6).getAmount());
        assertEquals(480,bomlist.getOrderLines().get(6).getMaterial().getLength());
        //test 7
        assertEquals(1,bomlist.getOrderLines().get(7).getAmount());
        assertEquals(600,bomlist.getOrderLines().get(7).getMaterial().getLength());
        assertEquals(2,bomlist.getOrderLines().get(8).getAmount());
        assertEquals(480,bomlist.getOrderLines().get(8).getMaterial().getLength());

    }

    @Test
    void addRafters() {
        Bomlist bomlist = new Bomlist();
        bomlist.addRafters(240, 480, woodList);
        bomlist.addRafters(540, 481, woodList);

        assertEquals(5,bomlist.getOrderLines().get(0).getAmount());
        assertEquals(480,bomlist.getOrderLines().get(0).getMaterial().getLength());

        assertEquals(10,bomlist.getOrderLines().get(1).getAmount());
        assertEquals(600,bomlist.getOrderLines().get(1).getMaterial().getLength());
    }

    @Test
    void calculatePrice() {
        Bomlist bomlist = new Bomlist();
        int totalPrice = 0;

        Material stolpe = null;
        Material spær = null;
        for (Material wood : woodList) {
            if (wood.getMaterialId() == 6) {
                stolpe = wood;
            } else if (wood.getMaterialId() == 5 && wood.getLength() == 480) {
                spær = wood;
            }
        }

        //4 stolper
        totalPrice += 4*stolpe.getPrice()*(stolpe.getLength()/100);

        //1 spær til rem
        totalPrice += spær.getPrice()*(spær.getLength()/100);

        //5 spær over rem
        totalPrice += 5*spær.getPrice()*(spær.getLength()/100);

        assertEquals(totalPrice, bomlist.calculatePrice(240, 240, woodList));
    }
}