package app.controllers;

import app.entities.Order;
import app.entities.User;
import app.exceptions.DatabaseException;
import app.persistence.ConnectionPool;
import app.persistence.OrderMapper;
import app.persistence.UserMapper;
import io.javalin.Javalin;
import io.javalin.http.Context;

import java.util.List;

public class UserController {


    public static void addRoutes(Javalin app, ConnectionPool connectionPool) {
        app.post("login", ctx -> login(ctx, connectionPool));
        app.post("loggingon", ctx -> loggingon(ctx, connectionPool));
        app.post("logout", ctx -> logout(ctx, connectionPool));
        app.post("createuserpage", ctx -> ctx.render("createuser.html"));
        app.post("carportcreationpage", ctx -> ctx.render("carportCreation.html"));
        app.post("createuser", ctx -> createUser(ctx, connectionPool));
        app.post("details", ctx -> details(ctx, connectionPool));
        app.post("mysite", ctx -> ctx.render("ordreoversigt.html"));

    }

    private static void login(Context ctx, ConnectionPool connectionPool) {
        ctx.sessionAttribute("loginPosition", ctx.formParam("buttonValue"));
        ctx.render("login.html");
    }

    private static void loggingon(Context ctx, ConnectionPool connectionPool) {

        String email = ctx.formParam("email");
        String password = ctx.formParam("password");

        try {
            User user = UserMapper.login(email, password, connectionPool);
            ctx.sessionAttribute("currentUser", user);


            if ("admin".equals(user.getRole())) {
                admin(ctx, connectionPool);
            } else {
                if ("1".equals(ctx.sessionAttribute("loginPosition"))) {
                    ctx.render("index.html");
                } else if ("2".equals(ctx.sessionAttribute("loginPosition"))) {
                    ctx.render("carportCreation.html");
                } else if ("3".equals(ctx.sessionAttribute("loginPosition"))) {
                    ctx.render("ordreoversigt.html");
                }
            }

        } catch (DatabaseException e) {

            ctx.attribute("message", e.getMessage());
            ctx.render("login.html");
        }


    }

    private static void createUser(Context ctx, ConnectionPool connectionPool) {
        String name = ctx.formParam("name");
        String password1 = ctx.formParam("password1");
        String password2 = ctx.formParam("password2");
        String email = ctx.formParam("email");
        String address = ctx.formParam("address");
        int postalcode = Integer.parseInt(ctx.formParam("postalcode"));
        String city = ctx.formParam("city");
        int phonenumber = Integer.parseInt(ctx.formParam("phonenumber"));


        // Validering af passwords - at de to matcher
        if (password1.equals(password2)) {
            try {
                UserMapper.createuser(name, email, password1, address, postalcode, city, phonenumber, connectionPool);
                ctx.attribute("message", "User created successfully.");
                ctx.render("index.html");
            } catch (DatabaseException e) {
                ctx.attribute("message", e.getMessage());
                ctx.render("createuser.html");
            }
        } else {
            ctx.attribute("message", "Passwords doesn't match!");
            ctx.render("createuser.html");
        }
    }

    public static void logout(Context ctx, ConnectionPool connectionPool) {

        // Invalidate session
        ctx.req().getSession().invalidate();
        ctx.redirect("/");
    }

    private static void admin(Context ctx, ConnectionPool connectionPool) throws DatabaseException {

        ctx.attribute("orderList", OrderMapper.getAllOrders(connectionPool));
        ctx.attribute("userList", UserMapper.getAllUsers(connectionPool));
        ctx.render("admin.html");
    }

    private static void details(Context ctx, ConnectionPool connectionPool) {
        int orderId = Integer.parseInt(ctx.formParam("orderId"));
        Order order = OrderMapper.getOrderById(orderId, connectionPool);
        ctx.attribute("orderDetails", order);
        ctx.attribute("oldprice", ctx.formParam("oldprice"));
        ctx.render("details.html");
    }


}
