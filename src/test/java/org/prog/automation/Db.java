package org.prog.automation;

import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import lombok.extern.slf4j.Slf4j;
import org.testng.Assert;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static org.prog.automation.Selenium.connection;

@Slf4j
public class Db {

    @When("I store them to DB")
    public void storeIphonesToDb() throws SQLException {
        List<String> codes = (List<String>) DataManager.DATA.get("iphone_codes");
        List<String> names = (List<String>) DataManager.DATA.get("iphone_names");

        log.info("DEBUG: Names count: " + (names != null ? names.size() : "null"));
        log.info("DEBUG: Codes count: " + (codes != null ? codes.size() : "null"));

        PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO iphones (Code, name) " +
                "VALUES (?, ?)");

        for (int i = 0; i < codes.size(); i++) {
            log.info("Writing to DB: Name = " + names.get(i) + ", Code = " + codes.get(i));
            preparedStatement.setString(1, codes.get(i));
            preparedStatement.setString(2, names.get(i));
            preparedStatement.execute();
        }
        log.info("All data successfully stored into DB");
    }

    @Then("I verify that iphones are correctly stored in DB")
    public void verifyIphonesInDb() throws SQLException {
        List<String> expectedCodes = (List<String>) DataManager.DATA.get("iphone_codes");
        List<String> expectedNames = (List<String>) DataManager.DATA.get("iphone_names");
        Assert.assertNotNull(expectedCodes, "Expected codes list from website is null!");
        Assert.assertNotNull(expectedNames, "Expected names list from website is null!");

        List<String> actualCodes = new ArrayList<>();
        List<String> actualNames = new ArrayList<>();

       try (PreparedStatement preparedStatement = connection.prepareStatement("Select Code, Name From iphones");

        ResultSet resultSet = preparedStatement.executeQuery()) {
            while (resultSet.next()) {
                actualCodes.add(resultSet.getString("Code"));
                actualNames.add(resultSet.getString("name"));
            }
        }

        Assert.assertEquals(actualCodes, expectedCodes, "The code lists does not match");
        Assert.assertEquals(actualNames, expectedNames, "The names lists does not match");

        log.info("Assertion PASSED! Data inside DB completely matches the scraped data.");
    }
}

