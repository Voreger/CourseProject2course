package framework.base;

import framework.managers.DriverManager;
import framework.managers.PageManager;
import org.openqa.selenium.*;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.DayOfWeek;
import java.time.Duration;
import java.time.LocalDate;
import java.time.format.TextStyle;
import java.util.*;
import java.util.NoSuchElementException;

public class BasePage {

    protected DriverManager driverManager = DriverManager.getInstance();
    protected PageManager pageManager = PageManager.getInstance();
    protected WebDriverWait wait = new WebDriverWait(driverManager.getDriver(), Duration.ofSeconds(5), Duration.ofMillis(100));

    public BasePage() {
        PageFactory.initElements(driverManager.getDriver(), this);
    }

    public void fillInput(WebElement input, String text) {
        waitUntilElementToBeClickable(input);
        input.click();
        input.clear();
        input.sendKeys(text);
    }

    public void buttonClick(WebElement button) {
        waitUntilElementToBeClickable(button);
        button.click();

    }

    public void scrollToElement(WebElement element) {
        JavascriptExecutor javascriptExecutor = (JavascriptExecutor) DriverManager.getInstance().getDriver();
        javascriptExecutor.executeScript("arguments[0].scrollIntoView(true);", element);
    }

    protected void waitUntilElementToBeVisible(WebElement element) {
        wait.until(ExpectedConditions.visibilityOf(element));
    }


    protected void waitUntilElementToBeClickable(WebElement element) {
        wait.until(ExpectedConditions.elementToBeClickable(element));
    }

    protected void waitSeconds(int seconds) {
        try {
            Thread.sleep(seconds * 1000L);
        }catch (InterruptedException e) {
            throw new RuntimeException(e);
        }


    }

    protected void waitUntilAttributeToBe(WebElement element, String attribute, String value) {
        wait.until(ExpectedConditions.attributeToBe(element, attribute, value));
    }


    protected void selectLastTab() {

        Set<String> windowHandles = driverManager.getDriver().getWindowHandles();

        for (String windowHandle : windowHandles) {
            driverManager.getDriver().switchTo().window(windowHandle);
        }
    }

    protected String getTodayDate() {
        LocalDate date = LocalDate.now();
        DayOfWeek day = date.getDayOfWeek();
        String str = day.getDisplayName(TextStyle.FULL, Locale.getDefault());
        return str;
    }

}
