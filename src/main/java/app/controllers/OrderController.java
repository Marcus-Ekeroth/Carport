package app.controllers;

import app.entities.Material;
import app.entities.Order;
import app.entities.User;
import app.exceptions.DatabaseException;
import app.persistence.*;
import io.javalin.Javalin;
import io.javalin.http.Context;

import java.util.List;

import static app.persistence.OrderMapper.createOrder;

public class OrderController {
    public static void addRoutes(Javalin app, ConnectionPool connectionPool) {

        app.post("/approveOrder", ctx -> approveOrder(ctx, connectionPool));
        app.post("/cancelOrder", ctx -> cancelOrder(ctx, connectionPool));
        app.post("/createOrder", ctx -> createOrder(ctx, connectionPool));
        app.post("/updatePrice", ctx -> updatePrice(ctx, connectionPool));
        app.post("/changeStatus", ctx -> changeStatus(ctx, connectionPool));

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

    private static void createOrder(Context ctx, ConnectionPool connectionPool) throws DatabaseException {

        int width = Integer.parseInt(ctx.formParam("carportWidth"));
        int length = Integer.parseInt(ctx.formParam("carportLength"));
        String roof = ctx.formParam("carportRoof");
        String shippingAddress = (ctx.formParam("ShippingAddress"));
        List<Material> woodList = MaterialMapper.getAllWood(connectionPool);

        Bomlist bomlist = new Bomlist();
        double price = bomlist.calculatePrice(length,width, woodList);

        try {
            OrderMapper.createOrder(ctx.sessionAttribute("currentUser"), width, length, roof, shippingAddress,connectionPool,price);
            ctx.attribute("message", "Order created successfully.");
            ctx.render("ordreoversigt.html");
        } catch (DatabaseException e) {
            ctx.attribute("message", e.getMessage());
            ctx.render("carportcreation.html");
        }

    }

    //Metode for at admin kan ændre pris på order
    private static void updatePrice(Context ctx, ConnectionPool connectionPool) throws DatabaseException {
        int orderId = Integer.parseInt(ctx.formParam("orderId"));
        double price = Double.parseDouble(ctx.formParam("newprice"));

        OrderMapper.updatePrice(orderId, price, connectionPool);
        ctx.attribute("orderDetails", OrderMapper.getOrderById(orderId, connectionPool));
        ctx.attribute("oldprice", ctx.formParam("oldprice"));
        ctx.render("details.html");
    }

    private static void changeStatus(Context ctx, ConnectionPool connectionPool) throws DatabaseException {
        try {
            int orderId = Integer.parseInt(ctx.formParam("order_id"));
            int orderStatus = Integer.parseInt(ctx.formParam("orderStatus"));
            OrderMapper.changeStatus(orderId, orderStatus, connectionPool);
            Order order = OrderMapper.getOrderById(orderId, connectionPool);
            ctx.attribute("orderDetails", order);
            ctx.render("details.html");
        } catch (NumberFormatException e) {
            System.out.println(e.getMessage());
        }

    }
}
