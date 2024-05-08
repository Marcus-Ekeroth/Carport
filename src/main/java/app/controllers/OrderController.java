package app.controllers;

import app.entities.Order;
import app.entities.User;
import app.exceptions.DatabaseException;
import app.persistence.ConnectionPool;
import app.persistence.OrderMapper;
import app.persistence.UserMapper;
import io.javalin.Javalin;
import io.javalin.http.Context;

import static app.persistence.OrderMapper.createOrder;

public class OrderController {
    public static void addRoutes(Javalin app, ConnectionPool connectionPool) {

        app.post("/approveOrder", ctx -> approveOrder(ctx, connectionPool));
        app.post("/cancelOrder", ctx -> cancelOrder(ctx, connectionPool));
        app.post("/createOrder", ctx -> createOrder(ctx, connectionPool));
        app.post("/updatePrice", ctx -> updatePrice(ctx, connectionPool));
    }


    //checkstatus skal nok være en list af orders som looper gennem
    public static void checkStatus(Context ctx, ConnectionPool connectionPool, Order order) {
        switch (order.getStatusId()) {
            case 1:
                ctx.attribute("status", "Afventer godkendelse");
            case 2:
                ctx.attribute("status", "Afventer Betaling");
            case 3:
                ctx.attribute("status", "Betalt");
            case 4:
                ctx.attribute("status", "Annulleret");

        }


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

        private static void createOrder (Context ctx, ConnectionPool connectionPool){

            int width = Integer.parseInt(ctx.formParam("carportWidth"));
            int length = Integer.parseInt(ctx.formParam("carportLength"));
            String roof = ctx.formParam("carportRoof");
            String shippingAddress = (ctx.formParam("ShippingAddress"));

            try {
                OrderMapper.createOrder(ctx.sessionAttribute("currentUser"), width, length, roof, shippingAddress, connectionPool);
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
        ctx.attribute("orderDetails", OrderMapper.getOrderById(orderId,connectionPool));
        ctx.render("details.html");
    }

    }
