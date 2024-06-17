package lambda.base;

import framework.managers.DriverManager;
import framework.managers.InitManager;
import framework.managers.PageManager;
import framework.managers.TestPropManager;
import framework.utils.PropsConst;
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
        System.out.println("--------------Тест-кейс LambdaTestSampleApp------------------");
        driverManager.getDriver().get(propManager.getProperty(PropsConst.LAMBDA_URL)); //Открываем ссылку
    }

    @AfterClass
    public static void after() {
        System.out.println("--------------Тест-кейс LambdaTestSampleApp------------------");
        InitManager.quitFramework();
    }
}
