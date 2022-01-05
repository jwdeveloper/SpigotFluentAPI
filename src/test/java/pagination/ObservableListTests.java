package pagination;


import jw.spigot_fluent_api.utilites.pagination.Pagination;
import org.bukkit.Bukkit;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;

import java.util.ArrayList;

public class ObservableListTests
{
    @Ignore
    @Test()
    public void testContentManager() {
        var max = 28;
        Pagination<String> contentManager = new Pagination<>(max);
        ArrayList<String> data = new ArrayList<>();
        for(int i=0;i<max*2+3;i++)
        {
            data.add("value"+i);
        }
        contentManager.setContent(data);

        var c1 = contentManager.canBackPage();
        var d1 = contentManager.getCurrentPageContent();
        var d2 = contentManager.nextPage();
        var d3 = contentManager.nextPage();
        var c2 = contentManager.canNextPage();
        var maxPageContent = contentManager.getMaxContentOnPage();
        var maxPages = contentManager.getPagesCount();
        Assertions.assertEquals(maxPageContent,max);
        Assertions.assertEquals(maxPages,3);
        Assertions.assertFalse(c1);
        Assertions.assertFalse(c2);
        Assertions.assertEquals(d1.size(),max);
        Assertions.assertEquals(d2.size(),max);
        Assertions.assertEquals(d3.size(),3);
    }
}
