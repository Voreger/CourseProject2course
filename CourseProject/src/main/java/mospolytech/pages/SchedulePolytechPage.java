package mospolytech.pages;

import framework.base.BasePage;
import io.qameta.allure.Step;
import org.junit.Assert;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.Set;

public class SchedulePolytechPage extends BasePage {

    @FindBy(xpath = "//h1")
    private WebElement title;

    @FindBy(xpath = "//a[@href='https://rasp.dmami.ru/']")
    private WebElement openSiteButton;

    @Step("Проверка что загрузилась нужная страница")
    public SchedulePolytechPage checkTitle(String expectedTitle){
        Assert.assertEquals("Заголовок не совпадает!", expectedTitle, title.getText());
        System.out.println("Страница загружена");
        return pageManager.getSchedulePolytechPage();
    }

    @Step("Скролл и нажатие на кнопку 'Смотреть на сайте'")
    public DmamiPage clickOpenSiteButton(){
        scrollToElement(openSiteButton);
        buttonClick(openSiteButton);
        selectLastTab();
        System.out.println("Кнопка нажата");
        return pageManager.getDmamiPage();
    }
}
