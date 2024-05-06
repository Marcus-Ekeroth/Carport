package app.controllers;

import app.entities.Order;
import app.exceptions.DatabaseException;
import app.persistence.ConnectionPool;
import app.persistence.OrderMapper;
import io.javalin.Javalin;
import io.javalin.http.Context;

public class OrderController {
    public static void addRoutes(Javalin app, ConnectionPool connectionPool) {

        app.post("admin", ctx -> approveOrder(ctx, connectionPool));

    }


    public static void checkStatus(Context ctx, ConnectionPool connectionPool, Order order){
        switch(order.getStatusId()){
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
    public static void approveOrder( Context ctx, ConnectionPool connectionPool) throws DatabaseException {
        int orderId = Integer.parseInt(ctx.formParam("orderId"));
        Order order = ctx.sessionAttribute("order");
        OrderMapper.approveOrder(orderId,order.getStatusId(), connectionPool);
        ctx.attribute("orderList",OrderMapper.getAllOrders(connectionPool));
        ctx.render("admin.html");
    }

}
