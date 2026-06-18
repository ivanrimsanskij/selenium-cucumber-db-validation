package org.prog.automation;

import io.cucumber.java.After;
import java.sql.PreparedStatement;
import java.sql.SQLException;


public class Hooks {
    @After("@database")
    public void cleanUpDb() throws SQLException {
        if (Selenium.getConnection()!= null) {
            try (PreparedStatement statement =
                    Selenium.getConnection().prepareStatement("Truncate Table iphones")) {
            statement.execute();
            }
        }
    }
}