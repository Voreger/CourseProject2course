package yandexmarket.pages;

import framework.base.BasePage;
import io.qameta.allure.Step;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.List;

public class CategoryYandexMarketPage extends BasePage {

    @FindBy(xpath = "//ul[contains(@data-autotest-id,\"subItems\")]//a")
    private List<WebElement> categories;

    @Step("Выбор категории '{expectedCategory}'")
    public ProductYandexMarketPage selectCategory(String expectedCategory){
        for (WebElement category: categories){
            if (category.getText().equals(expectedCategory)) {
                buttonClick(category);
                System.out.println("Категория " + expectedCategory + " выбрана");
                return pageManager.getProductYandexMarketPage();
            }
        }
        Assert.fail("Категория " + expectedCategory + " не найдена");
        return pageManager.getProductYandexMarketPage();
    }
}
