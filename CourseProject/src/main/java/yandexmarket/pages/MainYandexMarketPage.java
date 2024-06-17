package yandexmarket.pages;

import framework.base.BasePage;
import io.qameta.allure.Step;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.List;

public class MainYandexMarketPage extends BasePage {

    @FindBy(xpath = "//link[contains(@rel,'canonical')]")
    private WebElement link;

    @FindBy(xpath = "//div[contains(@data-baobab-name,'catalog')]//button")
    private WebElement catalogButton;

    @FindBy(xpath = "//ul[contains(@class,'_2GpS3')]//span")
    private List<WebElement> leftMenuSpans;

    @Step("Проверка загрузки главной страницы")
    public MainYandexMarketPage checkPageUploaded(){
        Assert.assertTrue("Страница не загружена!", link.getAttribute("href").contains("market.yandex"));
        System.out.println("Страница загружена!");
        return pageManager.getMainYandexMarketPage();
    }

    @Step("Нажатие на кнопку 'Каталог'")
    public MainYandexMarketPage catalogButtonClick(){
        buttonClick(catalogButton);
        System.out.println("Кнопка 'Каталог' нажата");
        return pageManager.getMainYandexMarketPage();
    }

    @Step("Выбор категории '{type}'")
    public CategoryYandexMarketPage selectCategoryType(String type){
        for (WebElement categoryTypeSpan: leftMenuSpans){
            if (categoryTypeSpan.getText().equals(type)){
                WebElement categoryTypeButton = categoryTypeSpan.findElement(By.xpath("./.."));
                buttonClick(categoryTypeButton);
                System.out.println("Категория " + type + " выбрана");
                return pageManager.getCategoryYandexMarketPage();
            }
        }
        Assert.fail("Категория " + type + " не найдена");
        return pageManager.getCategoryYandexMarketPage();
    }


}
