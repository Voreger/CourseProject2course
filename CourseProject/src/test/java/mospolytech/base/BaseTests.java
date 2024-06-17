package mospolytech.base;

import framework.managers.DriverManager;
import framework.managers.InitManager;
import framework.managers.PageManager;
import framework.managers.TestPropManager;
import framework.utils.PropsConst;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;

public class BaseTests {
    private final DriverManager driverManager = DriverManager.getInstance();
    private final TestPropManager propManager = TestPropManager.getInstance();
    protected PageManager pageManager = PageManager.getInstance();

    @BeforeClass
    public static void beforeClass() {
        InitManager.initFramework();
    }

    @Before
    public void before() {
        System.out.println("--------------Тест-кейс Московского политеха------------------");
        driverManager.getDriver().get(propManager.getProperty(PropsConst.MOSPOLYTECH_URL)); //Открываем ссылку
    }
    @After
    public void after(){}

    @AfterClass
    public static void afterClass() {
        System.out.println("--------------Тест-кейс Московского политеха------------------");
        InitManager.quitFramework();
    }
}
