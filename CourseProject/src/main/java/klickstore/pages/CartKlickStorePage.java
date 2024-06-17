package klickstore.pages;

import framework.base.BasePage;
import framework.utils.Product;
import io.qameta.allure.Step;
import org.junit.Assert;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.List;

public class CartKlickStorePage extends BasePage {

    @FindBy(xpath = "//h1")
    private WebElement cartTitle;

    @FindBy(xpath = "//div[@class=\"cart-total__body\" and @data-total-price]")
    private WebElement totalPrice;

    @Step("Проверка заголовка корзины")
    public CartKlickStorePage checkCartTitle(){
        Assert.assertEquals("Заголовок не совпадает!", "Корзина", cartTitle.getText());
        System.out.println("Заголовок совпадает!");
        return pageManager.getCartKlickStorePage();
    }

    @Step("Проверка совпадения суммы")
    public CartKlickStorePage checkProduct(List<Product> products, int count){
        int sum = 0;
        String price = totalPrice.getAttribute("data-total-price").replaceAll(" ","");
        for (int i = 0; i < count; i++) {
            sum += products.get(i).getIntPrice();
        }
        Assert.assertEquals("Сумма не совпадает", sum, Integer.parseInt(price));
        System.out.println("Сумма совпадает");
        return pageManager.getCartKlickStorePage();
    }

}
