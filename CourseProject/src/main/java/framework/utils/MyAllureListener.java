package framework.utils;

import io.qameta.allure.junit4.AllureJunit4;
import framework.managers.DriverManager;
import org.junit.runner.notification.Failure;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class MyAllureListener extends AllureJunit4 {

    @Override
    public void testFailure(Failure failure) {
        byte[] byteImage = ((TakesScreenshot)DriverManager.getInstance().getDriver()).getScreenshotAs(OutputType.BYTES);
        String methodName = failure.getDescription().getMethodName();

        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm:ss");
        String testDate = now.format(dateFormatter);
        String testTime = now.format(timeFormatter);
        String fileName = methodName + "_" + testDate + "_" + testTime;

        getLifecycle().addAttachment(fileName,"image/png", "png", byteImage);
        super.testFailure(failure);
    }

}
