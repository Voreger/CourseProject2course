package lambda;

import lambda.base.BaseTests;
import org.junit.Ignore;
import org.junit.Test;

public class LambdaTestSampleAppTest extends BaseTests {

    @Test
    public void lambdaTest(){
        pageManager.getMainLambdaPage()
                .checkTitle()
                .checkRemainingText(5, 5);
        for (int i = 1; i <= 5; i++) { //цикл перебирающий все элементы списка
            pageManager.getMainLambdaPage()
                    .checkItem(i)
                    .clickItem(i);
        }
        pageManager.getMainLambdaPage()
                .createItem()
                .checkDisplayedItem(6)
                .checkItem(6)
                .clickItem(6);




//        mainPage.checkTitle();
//        mainPage.checkRemainingText(5, 5);
//        for (int i = 1; i <= 5; i++) { //цикл перебирающий все элементы списка
//            mainPage.checkItem(i);
//        }
//        mainPage.createItem();
//        mainPage.checkDisplayedItem(6);
//        mainPage.checkItem(6);

//        WebElement title = driver.findElement(By.xpath("//h2")); //Находим заголовок
//        Assert.assertEquals("Заголовок не совпадает!", "LambdaTest Sample App", title.getText()); //Проверка шага 1
//        System.out.println("Заголовок совпал");
//        WebElement remainingText = driver.findElement(By.xpath("//span[@class='ng-binding']")); //Находим remaining текст
//        Assert.assertEquals("Текст не совпадает!","5 of 5 remaining" , remainingText.getText()); //Проверка шага 2
//        System.out.println("Remaining текст совпал");
//        for (int i = 1; i <= 5; i++) { //цикл перебирающий все элементы списка
//            WebElement item = driver.findElement(By.xpath("//input[@name=\"li" + i + "\"]/../span"));
//            Assert.assertEquals("Элемент зачеркнут!", "done-false", item.getAttribute("class")); //Проверка шага 3
//            System.out.println("Элемент " + i + " найден и не зачеркнут");
//
//            WebElement itemCheckbox = driver.findElement(By.xpath("//input[@name=\"li" + i + "\"]")); //Находим чекбокс
//            itemCheckbox.click(); //Имитируем нажатие на чекбокс
//            Assert.assertTrue("Элемент " + i + " не отметился!", item.getAttribute("class").equals("done-true")); //Проверка шага 4
//            System.out.println("Элемент " + i + " отмечен");
//            Assert.assertTrue("Remaining текст не изменился", remainingText.getText().equals((5 - i) +" of 5 remaining"));
//            System.out.println("Remaining текст изменен");
//        }
//        WebElement addItemInput = driver.findElement(By.xpath("//input[@id=\"sampletodotext\"]"));
//        WebElement addItemButton = driver.findElement(By.xpath("//input[@id=\"addbutton\"]"));
//        addItemInput.click();
//        addItemInput.clear();
//        addItemInput.sendKeys("Next item");
//        System.out.println("Введено название пункта");
//        addItemButton.click();
//        System.out.println("Кнопка нажата");
//        WebElement item = driver.findElement(By.xpath("//input[@name=\"li6\"]/../span"));
//        Assert.assertTrue("Элемент не появился!", item.isDisplayed());
//        Assert.assertEquals("Элемент зачеркнут!", "done-false", item.getAttribute("class"));
//        System.out.println("Элемент 6 появился и не зачеркнут");
//        WebElement itemCheckbox = driver.findElement(By.xpath("//input[@name=\"li6\"]")); //Находим чекбокс
//        itemCheckbox.click(); //Имитируем нажатие на чекбокс
//        Assert.assertTrue("Элемент 6 не отметился!", item.getAttribute("class").equals("done-true")
//                && remainingText.getText().equals(("0 of 6 remaining")));
//        System.out.println("Remaining текст изменен");
    }
}
