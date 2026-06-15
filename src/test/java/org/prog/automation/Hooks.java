package org.prog.automation;

import io.cucumber.java.After;

import java.sql.PreparedStatement;
import java.sql.SQLException;

import static org.prog.automation.Selenium.connection;

public class Hooks {
    @After("@database")
    public void cleanUpDb() throws SQLException {
        if (connection != null) {
            PreparedStatement statement =
                    connection.prepareStatement("Truncate Table iphones");
            statement.execute();
            }
    }
}