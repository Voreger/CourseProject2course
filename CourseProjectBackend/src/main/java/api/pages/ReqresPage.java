package api.pages;

import api.managers.ManagerPropertiesTest;
import io.qameta.allure.Step;
import org.apache.log4j.Logger;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.List;

public class ReqresPage extends BasePage {

    private static final ManagerPropertiesTest props = ManagerPropertiesTest.getInstance();
    private static final Logger logger = Logger.getLogger(ReqresPage.class);

    @FindBy(xpath = "/html/body/div[1]/main/div/h2[1]")
    private WebElement title;

    @FindBy(xpath = "//li[@data-id]")
    private List<WebElement> buttonList;

    @FindBy(xpath = "//pre[@data-key='output-response']")
    private WebElement outputResponse;

    @FindBy(xpath = "//pre[@data-key='output-request']")
    private WebElement outputRequest;

    @FindBy(xpath = "//span[@class='url']")
    private WebElement urlRequest;

    @FindBy(xpath = "//span[contains(@class,'response-code')]")
    private WebElement responseCode;

    @Step("Проверка открытия страницы")
    public ReqresPage checkOpenPage() {
        checkOpenPage("Test your front-end against a real API", title);
        logger.info("Страница открыта!");
        return this;
    }

    @Step("Нажать на к {nameButton} и проверить ответ")
    public ReqresPage clickOnButtonAndCheckAPI(String nameButton, String method) {
        for (WebElement button : buttonList) {
            WebElement request = button.findElement(By.xpath("./a"));
            if (request.getText().equalsIgnoreCase(nameButton) && button.getAttribute("data-http").equalsIgnoreCase(method)) {

                moveToElement(button);
                button.click();
                waitUntilElementToBeVisible(outputResponse);
                Assert.assertEquals("Кнопка не нажата!", "active", button.getAttribute("class"));
                validateApiResponse(method, request);
                Assert.assertEquals("URL не совпадает!", request.getAttribute("href"), "https://reqres.in" + urlRequest.getText());

                logger.info("Ответ на '" + nameButton + "' соответствует API");
                return this;
            }
        }
        Assert.fail("Кнопка '" + nameButton + "' не найдена!");
        return this;
    }

    private void validateApiResponse(String httpMethod, WebElement request) {
        String requestUrl = request.getAttribute("href");
        String requestBody = outputRequest.getText();
        String responseBody = outputResponse.getText();
        int expectedStatusCode = Integer.parseInt(responseCode.getText());

        switch (httpMethod.toLowerCase()) {
            case "get":
                Assert.assertEquals("Ответ не совпадает!", get(requestUrl), responseBody);
                Assert.assertEquals("Статус не совпадает!", getStatusCode(requestUrl), expectedStatusCode);
                break;
            case "post":
                compareResponses(post(requestUrl, requestBody), responseBody);
                Assert.assertEquals("Статус код не совпадает!", postStatusCode(requestUrl, requestBody), expectedStatusCode);
                break;
            case "put":
                compareResponses(put(requestUrl, requestBody), responseBody);
                Assert.assertEquals("Статус не совпадает!", putStatusCode(requestUrl, requestBody), expectedStatusCode);
                break;
            case "patch":
                compareResponses(patch(requestUrl, requestBody), responseBody);
                Assert.assertEquals("Статус не совпадает!", patchStatusCode(requestUrl, requestBody), expectedStatusCode);
                break;
            case "delete":
                Assert.assertEquals("Ответ не совпадает!", delete(requestUrl), responseBody);
                Assert.assertEquals("Статус не совпадает!", deleteStatusCode(requestUrl), expectedStatusCode);
                break;
            default:
                Assert.fail("Некорректный метод: " + httpMethod);
        }
    }
}
