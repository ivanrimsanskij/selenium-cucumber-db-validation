package org.prog.automation;

import io.cucumber.java.en.Given;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.sql.Connection;
import java.time.Duration;
import java.util.List;

public class Selenium {

    private static WebDriver driver;
    private static AlloPage alloPage;
    private static Connection connection;

    public static WebDriver getDriver() { return driver; }
    public static AlloPage getAlloPage() { return alloPage; }
    public static Connection getConnection() { return connection; }

    public static void setDriver(WebDriver d) { driver = d; }
    public static void setAlloPage(AlloPage p) { alloPage = p; }
    public static void setConnection(Connection c) { connection = c; }

    @Given("I request {int} iphones from Allo.ua")
    public void requestingIphones(int amount) {
        alloPage.loadPage();

        WebElement input = alloPage.clickOnSearchInput();
        alloPage.pasteValue(input, "iphone");
        alloPage.clickOnSubmitButton();

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
        wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.className("product-sku__value")));

        List<String> codes = alloPage.getItemCode(amount);
        List<String> names = alloPage.getProductName(amount);

        DataManager.DATA.put("iphone_codes", codes);
        DataManager.DATA.put("iphone_names", names);
    }
}