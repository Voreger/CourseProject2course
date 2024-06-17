package klickstore.pages;

import framework.base.BasePage;
import io.qameta.allure.Step;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.List;

public class MainKlickStorePage extends BasePage {

    @FindBy(xpath = "//ul[@class='top-menu']//a[@href='/']/..")
    private WebElement title;

    @FindBy(xpath = "//div[contains(@class,'burg-desctop')]")
    private WebElement catalog;

    @FindBy(xpath = "//li[@data-f-name]/a")
    private List<WebElement> categories;


    @Step("Проверка загрузки страницы")
    public MainKlickStorePage checkTitle(){
        Assert.assertTrue("Страница не загружена!", title.getAttribute("class").contains("active"));
        System.out.println("Страница загружена!");
        return pageManager.getMainKlickStorePage();
    }

    @Step("Открытие каталога и выбор категории")
    public CategoryKlickStorePage selectCategory(String expectedCategory){
        buttonClick(catalog);
        Assert.assertTrue("Каталог не открылся!", catalog.getAttribute("class").contains("active"));
        System.out.println("Каталог открыт");
        for (WebElement category : categories){
            String categoryText = category.findElement(By.xpath("./span")).getText();
            if (categoryText.equalsIgnoreCase(expectedCategory)) {
                buttonClick(category);
                return pageManager.getCategoryKlickStorePage();
            }
        }
        Assert.fail("Категория " + expectedCategory + " не найдена");
        return pageManager.getCategoryKlickStorePage();
    }


}
