package org.prog.automation;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;

import java.sql.DriverManager;

import static org.prog.automation.Selenium.connection;
import static org.prog.automation.Selenium.driver;

@CucumberOptions(
        tags = "@dbHw",
        glue = "org.prog.automation",
        features = "src/test/resources"
)

public class CucumberRunnerTest extends AbstractTestNGCucumberTests {
    @BeforeSuite
    private void beforeSuite() throws Exception {
        ChromeOptions options = new ChromeOptions();

        options.addArguments("--disable-notifications");
        options.addArguments("--headless=new");
        options.addArguments("--user-agent=Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/147.0.0.0 Safari/537.36");
        options.addArguments("--no-sandbox");
        options.addArguments("--start-maximized");
        options.addArguments("--disable-popup-blocking");
        options.addArguments("--window-size=1920,1080");

        driver = new ChromeDriver(options);
        Selenium.alloPage = new AlloPage(driver);

        String url = "jdbc:mysql://localhost:3306/iphones";
        String user = System.getenv("Db_User");
        String password = System.getenv("Db_Password");
        connection = DriverManager.getConnection(url, user, password);
    }

    @AfterSuite
    public void afterSuite() throws Exception {
        if (connection != null) {
            connection.close();
        }
        if (driver != null) {
            driver.quit();
        }
    }
}