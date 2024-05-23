package app.persistence;

import app.entities.Order;
import app.entities.User;
import app.exceptions.DatabaseException;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
//integrationstest for OrderMapper
class OrderMapperTest {

    private static final ConnectionPool connectionPool = ConnectionPool.getInstance();

    @BeforeAll
    static void setupClass()
    {
        try (Connection connection = connectionPool.getConnection())
        {
            try (Statement stmt = connection.createStatement()) {
                // The test schema is already created, so we only need to delete/create test tables
                stmt.execute("DROP TABLE IF EXISTS test.order CASCADE");
                stmt.execute("DROP TABLE IF EXISTS test.users CASCADE");
                stmt.execute("DROP SEQUENCE IF EXISTS test.users_user_id_seq CASCADE;");
                stmt.execute("DROP SEQUENCE IF EXISTS test.order_order_id_seq CASCADE;");
                // Create tables as copy of original public schema structure
                stmt.execute("CREATE TABLE test.users AS (SELECT * from public.users) WITH NO DATA");
                stmt.execute("CREATE TABLE test.order AS (SELECT * from public.order) WITH NO DATA");
                // Create sequences for auto generating id's for users and orders
                stmt.execute("CREATE SEQUENCE test.users_user_id_seq");
                stmt.execute("ALTER TABLE test.users ALTER COLUMN user_id SET DEFAULT nextval('test.users_user_id_seq')");
                stmt.execute("CREATE SEQUENCE test.order_order_id_seq");
                stmt.execute("ALTER TABLE test.order ALTER COLUMN order_id SET DEFAULT nextval('test.order_order_id_seq')");
            }
        }
        catch (SQLException e)
        {
            e.printStackTrace();
            fail("Database connection failed");
        }

    }


    @BeforeEach
    void setUp() {
        try (Connection connection = connectionPool.getConnection())
        {
            try (Statement stmt = connection.createStatement())
            {
                // Remove all rows from all tables
                stmt.execute("DELETE FROM test.order");
                stmt.execute("DELETE FROM test.users");

                stmt.execute("INSERT INTO test.users (user_id, email, password, address, postalcode, city, phonenumber, role, name) " +
                        "VALUES  (1, 'test@test.dk', '1234', 'testadresse', 0000, 'testby', '12345678', 'user', 'testname'), " +
                        "(2, 'test@admin.dk', '1234', 'testadresse 36', 1000, 'testby', '87654321', 'admin', 'testnametwo')");

                stmt.execute("INSERT INTO test.order (order_id, width, length, status_id, price, user_id, shipping_address, roof) " +
                        "VALUES (1, 600, 780, 1, 20000, 1, 'testaddresse', 'tag'), (2, 540, 700, 2, 15000, 2,'testaddresse', 'tag'), (3, 480, 600, 1, 14000, 1,'testaddresse', 'tag')");

                // Set sequence to continue from the largest order_id and user_id
                stmt.execute("SELECT setval('test.order_order_id_seq', COALESCE((SELECT MAX(order_id) + 1 FROM test.order), 1), false)");
                stmt.execute("SELECT setval('test.users_user_id_seq', COALESCE((SELECT MAX(user_id) + 1 FROM test.users), 1), false)");

            }
        }
        catch (SQLException e)
        {
            e.printStackTrace();
            fail("Database connection failed");
        }
    }

    @Test
    void getAllOrders() {
        try
        {
            int expected = 3;
            List<Order> actualOrders  = OrderMapper.getAllOrders(connectionPool);
            assertEquals(expected, actualOrders.size());
        }
        catch (DatabaseException e)
        {
            fail("Database fejl: " + e.getMessage());
        }
    }


    @Test
    void createOrder() {
        try
        {

            int expected = OrderMapper.getAllOrders(connectionPool).size();


            User user = new User(3,"testnavn", "test@test.dk","1234","testadresse",1234,"testby",12345678,"user");

            OrderMapper.createOrder(user,200,400,"tag","testadresse",connectionPool,2000);
            int newOrderCount = OrderMapper.getAllOrders(connectionPool).size();

            assertEquals(expected + 1, newOrderCount);
        }
        catch (DatabaseException e)
        {
            fail("Database fejl: " + e.getMessage());
        }
    }



    @Test
    void getOrderById() {
        try
        {
            User user = new User(1, "testname", "test@test.dk", "1234", "testadresse", 0000, "testby", 12345678, "user");
            Order expected = new Order(1, 20000, 600, 780, "tag", "testaddresse", user.getUserId(), "Afventer Godkendelse");
            Order dbOrder = OrderMapper.getOrderById(1, connectionPool);
            assertEquals(expected, dbOrder);
        }
        catch (RuntimeException e)
        {
            fail("Database fejl: " + e.getMessage());
        }
    }
}