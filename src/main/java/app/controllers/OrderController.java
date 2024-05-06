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

        app.post("admin", ctx -> approveOrder(ctx, connectionPool));
        app.post("/createOrder", ctx -> createOrder(ctx, connectionPool));
    }


    public static void checkStatus(Context ctx, ConnectionPool connectionPool, Order order) {
        switch (order.getStatusId()) {
            case 1:
                ctx.attribute("status", "Afventer godkendelse");
            case 2:
                ctx.attribute("status", "Afventer Betaling");
            case 3:
                ctx.attribute("status", "Betalt");
            case 4:
                ctx.attribute("status", "Anulleret");

        }


    }

    public static void approveOrder(Context ctx, ConnectionPool connectionPool) throws DatabaseException {
        int orderId = Integer.parseInt(ctx.formParam("orderId"));
        Order order = ctx.sessionAttribute("order");
        OrderMapper.approveOrder(orderId, order.getStatusId(), connectionPool);
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

    }
