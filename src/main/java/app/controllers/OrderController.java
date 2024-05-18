package app.controllers;


import app.entities.*;
import app.exceptions.DatabaseException;
import app.persistence.*;
import app.services.CarportSvg;
import app.services.Svg;
import io.javalin.Javalin;
import io.javalin.http.Context;

import java.util.List;
import java.util.Locale;

import static app.persistence.OrderMapper.createOrder;

public class OrderController {
    public static void addRoutes(Javalin app, ConnectionPool connectionPool) {

        app.post("/approveOrder", ctx -> approveOrder(ctx, connectionPool));
        app.post("/cancelOrder", ctx -> cancelOrder(ctx, connectionPool));
        app.post("/createOrder", ctx -> createOrder(ctx, connectionPool));
        app.post("/updatePrice", ctx -> updatePrice(ctx, connectionPool));
        app.post("/changeStatus", ctx -> changeStatus(ctx, connectionPool));
        app.post("pay", ctx -> pay(ctx,connectionPool));
        app.post("deleteOrder", ctx -> deleteOrder(ctx, connectionPool));
        app.post("/showCarport", ctx -> showCarport(ctx, connectionPool));

    }

    public static void approveOrder(Context ctx, ConnectionPool connectionPool) throws DatabaseException {
        int orderId = Integer.parseInt(ctx.formParam("orderId"));
        OrderMapper.changeStatus(orderId, 2, connectionPool); // statusid skal være 2 da den skal videre til case 2 eftersom udgangspunktet her er at admin har godkendt bestilling
        ctx.attribute("orderList", OrderMapper.getAllOrders(connectionPool));
        ctx.render("admin.html");
    }

    public static void cancelOrder(Context ctx, ConnectionPool connectionPool) throws DatabaseException {
        int orderId = Integer.parseInt(ctx.formParam("orderId"));
        OrderMapper.changeStatus(orderId, 4, connectionPool);
        ctx.attribute("orderList", OrderMapper.getAllOrders(connectionPool));
        ctx.render("admin.html");
    }

    private static void displayOrder(Context ctx, ConnectionPool connectionPool) throws DatabaseException {
        User user = ctx.sessionAttribute("currentUser");
        ctx.attribute("orderUserList", OrderMapper.getUserOrder(user, connectionPool));
        ctx.render("ordreoversigt.html");
    }


    private static void createOrder(Context ctx, ConnectionPool connectionPool) throws DatabaseException {

        try {
            int width = Integer.parseInt(ctx.formParam("carportWidth"));
            int length = Integer.parseInt(ctx.formParam("carportLength"));
            String roof = ctx.formParam("carportRoof");
            String shippingAddress = (ctx.formParam("ShippingAddress"));
            List<Material> woodList = MaterialMapper.getAllWood(connectionPool);

            Bomlist bomlist = new Bomlist();
            double price = bomlist.calculatePrice(length, width, woodList);
            int orderId = OrderMapper.createOrder(ctx.sessionAttribute("currentUser"), width, length, roof, shippingAddress, connectionPool, price);
            saveBomlist(orderId, bomlist, connectionPool);
            ctx.attribute("message", "Order created successfully.");
            displayOrder(ctx, connectionPool);

        } catch (DatabaseException e) {
            ctx.attribute("message", e.getMessage());
            ctx.render("carportcreation.html");
        } catch (NumberFormatException e) {
            ctx.attribute("message", "Udfyld alle felterne");
            ctx.render("carportcreation.html");

        }

    }

    private static void saveBomlist(int orderId, Bomlist bomlist, ConnectionPool connectionPool) throws DatabaseException {
        for (Bom bom: bomlist.getOrderLines()) {
            BomMapper.createBom(orderId, bom.getMaterial().getMaterialId(), bom.getMaterial().getMaterialVariantId(),  bom.getAmount(), connectionPool);
        }
    }

    //Metode for at admin kan ændre pris på order
    private static void updatePrice(Context ctx, ConnectionPool connectionPool) throws DatabaseException {
        int orderId = Integer.parseInt(ctx.formParam("orderId"));
        double price = Double.parseDouble(ctx.formParam("newprice"));

        OrderMapper.updatePrice(orderId, price, connectionPool);
        ctx.attribute("orderDetails", OrderMapper.getOrderById(orderId, connectionPool));
        ctx.attribute("oldprice", ctx.sessionAttribute("oldprice"));
        ctx.attribute("orderlines", BomMapper.getBomlistById(orderId,connectionPool).getOrderLines());
        ctx.render("details.html");
    }

    private static void changeStatus(Context ctx, ConnectionPool connectionPool) throws DatabaseException {
        try {
            int orderId = Integer.parseInt(ctx.formParam("order_id"));
            int orderStatus = Integer.parseInt(ctx.formParam("orderStatus"));
            OrderMapper.changeStatus(orderId, orderStatus, connectionPool);
            Order order = OrderMapper.getOrderById(orderId, connectionPool);
            ctx.attribute("orderDetails", order);
            ctx.attribute("oldprice", ctx.sessionAttribute("oldprice"));
            ctx.attribute("orderlines", BomMapper.getBomlistById(orderId,connectionPool).getOrderLines());
            ctx.render("details.html");
        } catch (NumberFormatException e) {
            ctx.attribute("message", "Vælg en status");
            ctx.render("details.html");
        }

    }

    private static void pay(Context ctx, ConnectionPool connectionPool) throws DatabaseException {
        int orderId = Integer.parseInt(ctx.formParam("orderId"));

        OrderMapper.changeStatus(orderId, 3, connectionPool);

        ctx.attribute("payedOrder",OrderMapper.getOrderById(orderId, connectionPool));
        ctx.attribute("orderlines", BomMapper.getBomlistById(orderId,connectionPool).getOrderLines());
        ctx.render("receipt.html");
    }

    private static void showCarport(Context ctx, ConnectionPool connectionPool) throws DatabaseException {
        try {
            int width = Integer.parseInt(ctx.formParam("carportWidth"));
            int length = Integer.parseInt(ctx.formParam("carportLength"));
            Locale.setDefault(new Locale("US"));
            CarportSvg svg = new CarportSvg(width, length);
            Svg outerSvg = new Svg(0, 0, "0 0 1000 1000", "auto");
            // tilføj pile til outerSvg
            outerSvg.addArrow(20, 20, 20, width+20, "Stroke: #000000");
            outerSvg.addArrow(20, width+20, length+20, width+20, "Stroke:#000000");
            // tegn carport i Svg objektet
            // indsæt Svg i outerSvg:
            outerSvg.addSvg(svg.getCarportSvg());

            ctx.attribute("svg", outerSvg.toString());
            ctx.render("showsvg.html");

        } catch (NumberFormatException e) {
            ctx.attribute("message", "Udfyld alle felterne");
            ctx.render("carportcreation.html");

        }
    }

    private static void deleteOrder(Context ctx, ConnectionPool connectionPool) throws DatabaseException{
        int orderId = Integer.parseInt(ctx.formParam("orderId"));
        OrderMapper.deleteOrderById(orderId, connectionPool);
        displayOrder(ctx, connectionPool);
    }
}
