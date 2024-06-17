package klickstore;

import framework.utils.Product;
import klickstore.base.BaseTests;
import org.junit.Test;

import java.util.List;

public class KlickStoreTest extends BaseTests {

    @Test
    public void klickStoreCategoryTest(){
        pageManager.getMainKlickStorePage()
                .checkTitle()
                .selectCategory("Мониторы")
                .checkTitle("Мониторы")
                .selectSort("Цена - возрастание")
                .outputProducts(4)
                .selectView("Прайс-лист")
                .compareProduct(1);
    }

    @Test
    public void klickStoreCartTest(){
        String category = "Клавиатуры";
        String sort = "Название - Я-А";
        int count = 2;

        pageManager.getMainKlickStorePage()
                .checkTitle()
                .selectCategory(category)
                .checkTitle(category)
                .selectSort(sort)
                .saveProducts(count)
                .addToCart(count)
                .openCart();

        List<Product> products  = pageManager.getCategoryKlickStorePage().getProducts();

        pageManager.getCartKlickStorePage()
                .checkCartTitle()
                .checkProduct(products,count);
    }

    @Test
    public void klickStoreComparisonTest(){
        String category = "Компьютеры";
        String sort = "Цена - убывание";
        int productCount = 2;

        pageManager.getMainKlickStorePage()
                .checkTitle()
                .selectCategory(category)
                .checkTitle(category)
                .selectSort(sort)
                .saveProducts(productCount)
                .addToCompare(productCount)
                .checkCompareCounter(productCount)
                .openCompare()
                .checkCompareTitle()
                .deleteFromCompare()
                .closeCompare()
                .checkCompareCounter(1);
    }

}
