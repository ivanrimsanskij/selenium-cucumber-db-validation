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

    public static WebDriver driver;
    public static AlloPage alloPage;
    public static Connection connection;

    @Given("I request {int} iphones from Allo.ua")
    public void requestingIphones(int amount) {
        alloPage.loadPage();

        WebElement input = alloPage.clickOnSearchInput();
        alloPage.pasteValue(input, "iphone");
        alloPage.clickOnSubmitButton();

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
        wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.className("product-sku__value")));

        List<String> codes = alloPage.getItemCode( amount);
        List<String> names = alloPage.getProductName( amount);

        DataManager.DATA.put("iphone_codes", codes);
        DataManager.DATA.put("iphone_names", names);
    }
}