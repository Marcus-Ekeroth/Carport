package app.controllers;/* @auther: Frederik Dupont */

import app.entities.User;
import app.exceptions.DatabaseException;
import app.persistence.ConnectionPool;
import app.persistence.OrderMapper;
import app.persistence.UserMapper;
import io.javalin.Javalin;
import io.javalin.http.Context;

import static app.persistence.OrderMapper.createOrder;

public class OrderController {
    public static void addRoutes(Javalin app, ConnectionPool connectionPool){
        app.post("/createOrder", ctx-> createOrder(ctx, connectionPool));
    }

    private static void createOrder(Context ctx, ConnectionPool connectionPool) {

        int width = Integer.parseInt(ctx.formParam("carportWidth"));
        int length = Integer.parseInt(ctx.formParam("carportLength"));
        String roof = ctx.formParam("carportRoof");
        String shippingAddress = (ctx.formParam("ShippingAddress"));

        try {
            OrderMapper.createOrder(ctx.sessionAttribute("currentUser"),width,length,roof,shippingAddress, connectionPool);
            ctx.attribute("message", "Order created successfully.");
            ctx.render("ordreoversigt.html");
        } catch (DatabaseException e) {
            ctx.attribute("message", e.getMessage());
            ctx.render("carportcreation.html");
        }

    }

}
