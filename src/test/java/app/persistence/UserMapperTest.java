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

class UserMapperTest {
    private static final ConnectionPool connectionPool = ConnectionPool.getInstance();

    @BeforeAll
    static void setupClass()
    {
        try (Connection connection = connectionPool.getConnection())
        {
            try (Statement stmt = connection.createStatement()) {
                // The test schema is already created, so we only need to delete/create test tables
                stmt.execute("DROP TABLE IF EXISTS test.users CASCADE");
                stmt.execute("DROP SEQUENCE IF EXISTS test.users_user_id_seq CASCADE;");
                // Create tables as copy of original public schema structure
                stmt.execute("CREATE TABLE test.users AS (SELECT * from public.users) WITH NO DATA");
                // Create sequences for auto generating id's for users and orders
                stmt.execute("CREATE SEQUENCE test.users_user_id_seq");
                stmt.execute("ALTER TABLE test.users ALTER COLUMN user_id SET DEFAULT nextval('test.users_user_id_seq')");
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
                stmt.execute("DELETE FROM test.users");

                stmt.execute("INSERT INTO test.users (user_id, email, password, address, postalcode, city, phonenumber, role, name) " +
                        "VALUES  (1, 'test@test.dk', '1234', 'testadresse', 0000, 'testby', '12345678', 'user', 'testname'), " +
                        "(2, 'test@admin.dk', '1234', 'testadresse 36', 1000, 'testby', '87654321', 'admin', 'testnametwo')");


                // Set sequence to continue from the largest order_id and user_id
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
    void createuser() {
        try
        {
            int expected=UserMapper.getAllUsers(connectionPool).size();
            UserMapper.createuser("testname","test2@test.dk","1234","testadresse",0000,"testby" ,12345678, connectionPool);
            int actual=UserMapper.getAllUsers(connectionPool).size();
            assertEquals(expected+1,actual);
        }
        catch (DatabaseException e)
        {
            fail("Database fejl: " + e.getMessage());
        }
    }
    }



