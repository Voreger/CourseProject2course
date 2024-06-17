package mospolytech;

import mospolytech.base.BaseTests;
import org.junit.Ignore;
import org.junit.Test;

public class MospolytechTest extends BaseTests {

    @Test
    public void mospolytechTest(){
        pageManager.getMainPolytechPage()
                .checkTitle("Московский Политех")
                .clickLeftMenuButton("Расписание")
                .checkTitle("Расписания")
                .clickOpenSiteButton()
                .checkTitle()
                .fillGroupInput("234") //221-36
                .selectGroup("234-221") //221-361
                .checkGroupSelected()
                .checkToday();
    }
}
