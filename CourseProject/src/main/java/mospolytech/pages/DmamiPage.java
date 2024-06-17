package mospolytech.pages;

import framework.base.BasePage;
import io.qameta.allure.Step;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.List;

public class DmamiPage extends BasePage {

    @FindBy(xpath = "//h4")
    private WebElement title;

    @FindBy(xpath = "//input[@class='groups']")
    private WebElement groupInput;

    @FindBy(xpath = "//div[contains(@class,'group') and @id]")
    private List<WebElement> groupButtons;

    @FindBy(xpath = "//div[@class = 'schedule']")
    private WebElement schedule;

    @FindBy(xpath = "//div[contains(@class,'schedule-day__title')]")
    private List<WebElement> scheduleDays;

    @Step("Проверяем что страница загрузилась")
    public DmamiPage checkTitle(){
        Assert.assertEquals("Заголовок не совпадает!", "Расписание занятий", title.getText());
        System.out.println("Страница загружена");
        return pageManager.getDmamiPage();
    }


    @Step("Вводим номер группы")
    public DmamiPage fillGroupInput(String groupNumber){
        waitUntilElementToBeClickable(groupInput);
        fillInput(groupInput, groupNumber);
        System.out.println("Номер группы введен");
        return pageManager.getDmamiPage();
    }

    @Step("Выбор нужной группы")
    public DmamiPage selectGroup(String groupNumber){
        for (WebElement groupButton: groupButtons){
            if (groupButton.getText().equals(groupNumber)) {
                scrollToElement(groupButton);
                buttonClick(groupButton);
                break;
            }
        }
        System.out.println("Группа выбрана");
        return pageManager.getDmamiPage();
    }

    @Step("Проверка выбора группы")
    public DmamiPage checkGroupSelected(){
        waitSeconds(1);
        Assert.assertTrue("Расписание для группы не найдено!", schedule.getAttribute("style").isEmpty());
        System.out.println("Расписание для группы найдено");
        return pageManager.getDmamiPage();
    }

    @Step("Проверка, что текущий день выделен цветом")
    public DmamiPage checkToday(){
        String today = getTodayDate();
        for (WebElement scheduleDay : scheduleDays){
            if (scheduleDay.getText().toLowerCase().equals(today)){
                String parentClass = scheduleDay.findElement(By.xpath("./..")).getAttribute("class");
                Assert.assertTrue("Текущий день не выделен цветом", parentClass.contains("today"));
                System.out.println("Текущий день выделен цветом");
                break;
            }
        }
        return pageManager.getDmamiPage();
    }
}
