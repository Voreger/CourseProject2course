package framework.managers;

import framework.utils.PropsConst;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.edge.EdgeDriver;

public class DriverManager {

    private WebDriver driver;

    private static TestPropManager testPropManager = TestPropManager.getInstance();

    private static DriverManager INSTANCE;

    private DriverManager(){
    }

    public static DriverManager getInstance(){
        if (INSTANCE == null){
            INSTANCE = new DriverManager();
        }
        return INSTANCE;
    }

    public WebDriver getDriver(){
        if (driver == null){
            initDriver();
        }
        return driver;
    }

    private void initDriver(){
        System.setProperty("webdriver.edge.driver", testPropManager.getProperty(PropsConst.PATH_EDGE_DRIVER_WINDOWS)); //Подключаем веб-драйвер edge
        driver = new EdgeDriver();
    }

    public void quitDriver(){
        if (driver != null){
            driver.quit();
            driver = null;
        }
    }
}
