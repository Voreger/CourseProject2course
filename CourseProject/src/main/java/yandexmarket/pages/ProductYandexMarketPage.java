package yandexmarket.pages;

import framework.base.BasePage;
import framework.managers.PageManager;
import framework.utils.Product;
import io.qameta.allure.Step;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindAll;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.FindBys;

import java.util.ArrayList;
import java.util.List;

public class ProductYandexMarketPage extends BasePage {

    @FindBy(xpath = "//div[@data-zone-name=\"Filter\"]//h4")
    private List<WebElement> filters;

    @FindBy(xpath = "//h1")
    private WebElement title;

    @FindBy(xpath = "//button[@data-baobab-name=\"sort\"]")
    private List<WebElement> sorts;

    @FindBy(xpath = "//div[@data-zone-name=\"QuickFilterButton\"]//span")
    private WebElement filterSpan;

    @FindBy(xpath = "//article[@data-auto=\"searchOrganic\"]")
    private List<WebElement> items;

    @FindBy(xpath = "//input[@id=\"header-search\"]")
    private WebElement searchInput;

    @FindBy(xpath = "//button[@data-auto=\"search-button\"]")
    private WebElement searchButton;

    @FindBy(xpath = "//h3")
    private List<WebElement> itemNames;


    private List<Product> products = new ArrayList<>();


    @Step("Проверка что открылась нужная страница")
    public ProductYandexMarketPage checkTitle(String expectedTitle) {
        Assert.assertEquals("Открылась не верная страница!", expectedTitle, title.getText());
        System.out.println("Страница " + expectedTitle + " открыта!");
        return pageManager.getProductYandexMarketPage();
    }

    @Step("Выбор фильтра '{filter}' : '{value}'")
    public ProductYandexMarketPage selectFilter(String expectedFilter, String expectedValue) {
        for (WebElement filter : filters) {
            if (filter.getText().equals(expectedFilter)) {
                System.out.println("Фильтр " + expectedFilter + " найден!");
                List<WebElement> values = filter.findElements(By.xpath("./../../..//div[contains(@data-baobab-name,\"filterValue\")]//span[@class=\"_1-LFf\"]"));
                for (WebElement value : values) {
                    if (value.getText().equals(expectedValue)) {
                        WebElement filterCheckbox = value.findElement(By.xpath("./../../..//label[contains(@role,\"checkbox\")]"));
                        buttonClick(filterCheckbox);
                        System.out.println("Значение " + expectedValue + " найдено!");
                        break;
                    }
                }
                return pageManager.getProductYandexMarketPage();
            }
        }
        Assert.fail("Фильтр " + expectedFilter + ": " + expectedValue + " не найден");
        return pageManager.getProductYandexMarketPage();
    }

    @Step("Выбор сортировки")
    public ProductYandexMarketPage selectSort(String sortType) {
        String sortTypeSite = "";
        switch (sortType) {
            case "Подешевле":
                sortTypeSite = "aprice";
                break;
            case "Популярные":
                sortTypeSite = "dpop";
                break;
            case "Подороже":
                sortTypeSite = "dprice";
                break;
            case "Высокий рейтинг":
                sortTypeSite = "rating";
                break;
            case "По скидке":
                sortTypeSite = "discount_p";
                break;
        }
        for (WebElement sort : sorts) {
            if (sort.getAttribute("data-autotest-id").equals(sortTypeSite)) {
                buttonClick(sort);
                waitSeconds(1);
                System.out.println("Сортировка выбрана!");
                return pageManager.getProductYandexMarketPage();
            }
        }
        Assert.fail("Выбранная сортировка не найдена!");
        return pageManager.getProductYandexMarketPage();
    }

    @Step("Проверка выбора фильтра")
    public ProductYandexMarketPage checkFilter(String filter) {
        Assert.assertEquals("Фильтр не выбралася!", filter, filterSpan.getText());
        return pageManager.getProductYandexMarketPage();
    }


    @Step("Вывод в консоль товаров и цен")
    public ProductYandexMarketPage printItems(int count) {
        for (int i = 0; i < count; i++) {
            WebElement item = items.get(i);
            String itemName = item.findElement(By.xpath(".//h3")).getText();
            String itemPrice = item.findElement(By.xpath(".//span[@data-auto=\"snippet-price-current\"]/span[@class=\"_1ArMm\"]")).getText();
            System.out.println("Товар " + (i + 1) + ": " + itemName + " " + itemPrice);
            products.add(new Product(itemName, itemPrice));
        }
        return pageManager.getProductYandexMarketPage();
    }


    @Step("Поиск продукта")
    public ProductYandexMarketPage searchProduct(int productNo) {
        fillInput(searchInput, products.get(productNo - 1).getName());
        buttonClick(searchButton);
        System.out.println("Продукт найден!");
        return pageManager.getProductYandexMarketPage();
    }


    @Step("Проверка цены продукта")
    public ProductYandexMarketPage checkProduct(int productNo) {
        waitSeconds(1);
        for (WebElement itemName : itemNames) {
            if (itemName.getText().contains(products.get(productNo - 1).getName())) {
                WebElement itemPrice = itemName.findElement(By.xpath("./../../../../..//span[@data-auto=\"snippet-price-current\"]/span[@class=\"_1ArMm\"]"));
                Assert.assertEquals("Цена не соответствует!", products.get(productNo-1).getPrice(), itemPrice.getText());
                System.out.println("Цена соответствует!");
                return pageManager.getProductYandexMarketPage();
            }
        }
        Assert.fail("Продукт не найден!");
        return pageManager.getProductYandexMarketPage();
    }


}
