package lambda.pages;

import framework.base.BasePage;
import io.qameta.allure.Step;
import org.junit.Assert;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.List;

public class MainLambdaPage extends BasePage {

    @FindBy(xpath = "//h2")
    private WebElement title;

    @FindBy(xpath = "//span[@class='ng-binding']")
    private WebElement remainingText;

    @FindBy(xpath = "//input[@name]/../span")
    private List<WebElement> itemSpans;

    @FindBy(xpath = "//input[@name]")
    private List<WebElement> itemCheckboxes;

    @FindBy(xpath = "//input[@id=\"sampletodotext\"]")
    private WebElement addItemInput;

    @FindBy(xpath = "//input[@id='addbutton']")
    private WebElement addItemButton;

    @Step("Проверка заголовока на соответствие")
    public MainLambdaPage checkTitle(){
        Assert.assertEquals("Заголовок не совпадает!", "LambdaTest Sample App", title.getText());
        System.out.println("Заголовок совпал");
        return pageManager.getMainLambdaPage();
    }

    @Step("Проверка remaining текста на соответствие")
    public MainLambdaPage checkRemainingText(int notEndCounter, int itemCounter){
        Assert.assertEquals("Текст не совпадает!", notEndCounter + " of " + itemCounter + " remaining", remainingText.getText());
        System.out.println("Remaining текст совпал");
        return pageManager.getMainLambdaPage();
    }

    @Step("Проверка того, что пункт '{itemNo}' не зачеркнут")
    public MainLambdaPage checkItem(int itemNo){
        WebElement item = itemSpans.get(itemNo-1);
        Assert.assertEquals("Элемент зачеркнут!", "done-false", item.getAttribute("class")); //Проверка шага 3
        System.out.println("Элемент " + itemNo + " найден и не зачеркнут");
        return pageManager.getMainLambdaPage();
    }

    @Step("Нажатие на чекбокс и проверка отметился ли пункт '{itemNo}'")
    public MainLambdaPage clickItem(int itemNo){
        WebElement item = itemSpans.get(itemNo-1);
        WebElement itemCheckbox = itemCheckboxes.get(itemNo-1);
        itemCheckbox.click(); //Имитируем нажатие на чекбокс
        Assert.assertTrue("Элемент " + itemNo + " не отметился!", item.getAttribute("class").equals("done-true")); //Проверка шага 4
        System.out.println("Элемент " + itemNo + " отмечен");
        checkRemainingText(itemSpans.size()-itemNo,itemSpans.size());
        return pageManager.getMainLambdaPage();
    }

    @Step("Создание нового пункта")
    public MainLambdaPage createItem(){
        fillInput(addItemInput, "Next item");
        System.out.println("Введено название пункта");
        buttonClick(addItemButton);
        System.out.println("Кнопка нажата");
        return pageManager.getMainLambdaPage();
    }

    @Step("Проверка отображения элемента")
    public MainLambdaPage checkDisplayedItem(int itemNo){
        WebElement item = itemSpans.get(itemNo-1);
        Assert.assertTrue("Элемент не появился!", item.isDisplayed());
        Assert.assertEquals("Элемент зачеркнут!", "done-false", item.getAttribute("class"));
        System.out.println("Элемент 6 появился и не зачеркнут");
        return pageManager.getMainLambdaPage();
    }


}
