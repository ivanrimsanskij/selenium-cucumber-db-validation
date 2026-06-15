package org.prog.automation;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;
import java.util.stream.Collectors;

public class AlloPage {
        private WebDriver driver;

        public AlloPage(WebDriver driver) {
            this.driver = driver;
        }

        public void loadPage() {
            driver.get("https://allo.ua/");
        }

        public WebElement clickOnSearchInput() {

            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
            WebElement searchInput = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("search-form__input")));
            searchInput.click();

            return searchInput;
        }

        public void pasteValue(WebElement input, String text) {
            input.clear();
            input.sendKeys(text);
        }

        public void clickOnSubmitButton() {
            WebElement submitButton = driver.findElement(By.className("search-form__submit-button"));
            submitButton.click();
        }

        public List<WebElement> getProductCards() {
            return driver.findElements(By.className("product-card"));
        }

    public List<String> getItemCode(int limit) {
        Actions actions = new Actions(driver);
        return getProductCards().stream()
                .limit(limit)
                .map(item -> {
                    actions.moveToElement(item).perform();
                    WebElement codeElement = item.findElement(By.className("product-sku__value"));
                    return codeElement.getAttribute("textContent");
                })
                .collect(Collectors.toList());
    }
        public List <String> getProductName(int limit) {
            return getProductCards().stream()
                    .limit(limit)
                    .map(item -> {
                        WebElement nameElement = item.findElement(By.className("product-card__title"));
                        return nameElement.getText();
                    })
                    .collect(Collectors.toList());
        }
    }