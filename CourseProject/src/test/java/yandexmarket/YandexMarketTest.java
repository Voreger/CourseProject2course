package yandexmarket;

import org.junit.Ignore;
import org.junit.Test;
import yandexmarket.base.BaseTests;

public class YandexMarketTest extends BaseTests {

    @Test
    public void yandexMarketTest(){
        pageManager.getMainYandexMarketPage()
                .checkPageUploaded()
                .catalogButtonClick()
                .selectCategoryType("Ноутбуки и компьютеры")
                .selectCategory("Планшеты")
                .selectFilter("Производитель", "Samsung")
                .checkFilter("Samsung")
                .selectSort("Подешевле")
                .printItems(5)
                .searchProduct(2)
                .checkProduct(2);
    }
}
