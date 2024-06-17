package klickstore.pages;

import framework.base.BasePage;
import framework.utils.Product;
import io.qameta.allure.Step;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.ArrayList;
import java.util.List;

public class CategoryKlickStorePage extends BasePage {
    @FindBy(xpath = "//h1")
    private WebElement title;

    @FindBy(xpath = "//div[contains(@class,\"sorting-block sorting\")]")
    private WebElement sortButton;

    @FindBy(xpath = "//div[@class=\"sorting-block__item\"]/a")
    private List<WebElement> sorts;

    @FindBy(xpath = "//form[contains(@class,\"product-item\")]")
    private List<WebElement> items;

    @FindBy(xpath = "//a[contains(@class,'shop-view')]")
    private List<WebElement> views;

    @FindBy(xpath = "//div[@id=\"shop2-cart-preview\"]//span[@class=\"gr-cart-total-amount\"]")
    private WebElement cartCounter;

    @FindBy(xpath = "//div[contains(@class,\"cart-block-btn cart_append\")]")
    private WebElement cartButton;

    @FindBy(xpath = "//label[@class=\"gr-compare-plus\"]")
    private List<WebElement> compareButtons;

    @FindBy(xpath = "//a[contains(@class,\"compare-desc\")]/span")
    private WebElement compareCounter;

    @FindBy(xpath = "//a[contains(@class,\"compare-desc\")]")
    private WebElement compareButton;

    @FindBy(xpath = "//tr//h1")
    private WebElement compareTitle;

    @FindBy(xpath = "//a[@class=\"shop2-compare-delete\"]")
    private List<WebElement> compareDeleteButtons;


    @FindBy(xpath = "//div[contains(@class,\"compare-remodal\")]//button[@class=\"remodal-close-btn\"]")
    private WebElement compareExitButton;


    private List<Product> products = new ArrayList<>();

    @Step("Проверка загрузки страницы '{expectedTitle}'")
    public CategoryKlickStorePage checkTitle(String expectedTitle){
        Assert.assertEquals("Ожидаемая страница не загрузилась!", expectedTitle.toLowerCase(), title.getText().toLowerCase());
        return pageManager.getCategoryKlickStorePage();
    }

    @Step("Выбор сортировки '{expectedSort}'")
    public CategoryKlickStorePage selectSort(String expectedSort){
        buttonClick(sortButton);
        for (WebElement sort : sorts){
            if (sort.getText().equals(expectedSort)){
                buttonClick(sort);
                System.out.println("Сортировка выбрана!");
                return pageManager.getCategoryKlickStorePage();
            }
        }
        Assert.fail("Сортировка '" + expectedSort + "' не найдена!");
        return pageManager.getCategoryKlickStorePage();
    }

    @Step("Вывод '{count}' товаров в консоль")
    public CategoryKlickStorePage outputProducts(int count){
        for (int i = 0; i < count; i++) {
            String itemPrice = items.get(i).findElement(By.xpath(".//strong")).getText();
            String itemName = items.get(i).findElement(By.xpath(".//div[contains(@class,\"gr-product-name\")]/a")).getText();
            System.out.println("Товар " + (i+1) + ": " + itemName + " " + itemPrice);
            products.add(new Product(itemName,itemPrice));
        }
        return pageManager.getCategoryKlickStorePage();
    }

    @Step("Сохранение '{count}' товаров")
    public CategoryKlickStorePage saveProducts(int count){
        for (int i = 0; i < count; i++) {
            String itemPrice = items.get(i).findElement(By.xpath(".//strong")).getText();
            String itemName = items.get(i).findElement(By.xpath(".//div[contains(@class,\"gr-product-name\")]/a")).getText();
            products.add(new Product(itemName,itemPrice));
        }
        System.out.println("Первые " + count + " товаров сохранены");
        return pageManager.getCategoryKlickStorePage();
    }

    @Step("Выбор режима отображения '{viewName}'")
    public CategoryKlickStorePage selectView(String viewName){
        for (WebElement view : views){
            if (view.getAttribute("data-tooltip").equals(viewName)){
                buttonClick(view);
                System.out.println("Режим отображения " + viewName + " выбран");
                return pageManager.getCategoryKlickStorePage();
            }
        }
        Assert.fail("Режим отображения не найден!");
        return pageManager.getCategoryKlickStorePage();
    }

    @Step("Сравниваем '{no}' продукт с сохраненным")
    public CategoryKlickStorePage compareProduct(int no){
        String itemPrice = items.get(no-1).findElement(By.xpath(".//strong")).getText();
        String itemName = items.get(no-1).findElement(By.xpath(".//div[contains(@class,\"gr-product-name\")]/a")).getText();
        boolean conclusion = (itemName.equals(products.get(no-1).getName()) && itemPrice.equals(products.get(no-1).getPrice()));
        Assert.assertTrue("Товары не совпали!", conclusion);
        System.out.println("Товары совпали!");
        return pageManager.getCategoryKlickStorePage();
    }

    @Step("Добавление '{count}' товаров в корзину")
    public CategoryKlickStorePage addToCart(int count){
        for (int i = 0; i < count; i++) {
            WebElement addCartButton = items.get(i).findElement(By.xpath(".//button[contains(@class,\"buy\")]"));
            buttonClick(addCartButton);
            waitSeconds(1);
        }
        System.out.println("Первые " + count + " товаров добавлено в корзину");
        Assert.assertEquals("Количество товаров в корзине не совпало!", Integer.toString(count), cartCounter.getText());
        return pageManager.getCategoryKlickStorePage();
    }

    @Step("Открытие корзины")
    public CategoryKlickStorePage openCart(){
        buttonClick(cartButton);
        System.out.println("Кнопка нажата");
        return pageManager.getCategoryKlickStorePage();
    }

    @Step("Добавление '{count}' товаров в сравнение")
    public CategoryKlickStorePage addToCompare(int count){
        for (int i = 0; i < count; i++) {
            WebElement compareButton = compareButtons.get(0);
            buttonClick(compareButton);
            waitSeconds(1);
        }
        return pageManager.getCategoryKlickStorePage();
    }

    @Step("Проверка счетчика товаров в сравнении")
    public CategoryKlickStorePage checkCompareCounter(int count){
        Assert.assertEquals("Счетчик не совпадает!", Integer.toString(count),compareCounter.getText());
        return pageManager.getCategoryKlickStorePage();
    }

    @Step("Открытие блока 'Сравнение'")
    public CategoryKlickStorePage openCompare(){
        buttonClick(compareButton);
        System.out.println("Блок сравнения открыт");
        return pageManager.getCategoryKlickStorePage();
    }

    @Step("Проверка заголовка")
    public CategoryKlickStorePage checkCompareTitle(){
        Assert.assertEquals("Заголовок не совпадает!", "СРАВНЕНИЕ ТОВАРОВ:",compareTitle.getText());
        System.out.println("Заголовок совпал!");
        return pageManager.getCategoryKlickStorePage();
    }

    @Step("Удаление товара из сравнения")
    public CategoryKlickStorePage deleteFromCompare(){
        WebElement compareDeleteButton = compareDeleteButtons.get(0);
        buttonClick(compareDeleteButton);
        System.out.println("Товар удален!");
        return pageManager.getCategoryKlickStorePage();
    }

    @Step("Закрытие блока сравнение")
    public CategoryKlickStorePage closeCompare(){
        buttonClick(compareExitButton);
        System.out.println("Блок сравнения закрыт!");
        waitSeconds(1);
        return pageManager.getCategoryKlickStorePage();
    }

    public List<Product> getProducts(){
        return products;
    }


    public void clearProducts() {
        products.clear();
    }
}
