package api.managers;

import api.util.Constant;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.edge.EdgeDriver;

public class DriverManager {

    private WebDriver driver;
    private ManagerPropertiesTest propManager = ManagerPropertiesTest.getInstance();

    private static DriverManager INSTANCE = null;

    private DriverManager() {
    }

    public static DriverManager getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new DriverManager();
        }
        return INSTANCE;
    }

    public WebDriver getDriver() {
        if (driver == null) {
            initDriver();
        }
        return driver;
    }

    public void quitDriver() {
        if (driver != null) {
            driver.close();
            driver.quit();
            driver = null;
        }
    }

    private void initDriver() {
        System.setProperty("webdriver.edge.driver", propManager.getProperty(Constant.PATH_EDGE_DRIVER_WINDOWS));
        driver = new EdgeDriver();

    }
}