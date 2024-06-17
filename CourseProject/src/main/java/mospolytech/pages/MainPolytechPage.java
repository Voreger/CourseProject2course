package mospolytech.pages;

import framework.base.BasePage;
import io.qameta.allure.Step;
import org.junit.Assert;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.List;

public class MainPolytechPage extends BasePage {

    @FindBy(xpath = "//div[@class='navigation-menu__left-nav']//a[contains(@href,'/')]")
    private List<WebElement> leftMenu;

    @FindBy(xpath = "//h1")
    private WebElement title;

    @Step("Проверка что загрузилась нужная страница")
    public MainPolytechPage checkTitle(String expectedTitle){
        Assert.assertEquals("Заголовок не совпадает!", expectedTitle, title.getText());
        System.out.println("Страница загружена");
        return pageManager.getMainPolytechPage();
    }

    @Step("Нажатие на кнопку 'Расписание'")
    public SchedulePolytechPage clickLeftMenuButton(String buttonName){
        for (WebElement button : leftMenu){
            if (button.getAttribute("title").contains(buttonName)) {
                button.click();
                break;
            }
        }
        System.out.println("Кнопка нажата");
        return pageManager.getSchedulePolytechPage();
    }
}
