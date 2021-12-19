package pagination;

import jw.spigot_fluent_api.utilites.pagination.Pagination;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;

import java.util.ArrayList;

public class PaginationTests
{
    private static Pagination<String> mock;
    private static int maxContentOnSide = 5;
    @BeforeClass
    public static void setUp()
    {
        ArrayList<String> content = new ArrayList<>();
        for(int i =0;i<16;i++)
        {
            content.add(i+"");
        }
        mock = new Pagination<>(content, maxContentOnSide);
    }
    @Test
    public void ShouldGiveRightPageNumber()
    {
        mock.nextPage();
        var pageNumber = mock.getCurrentPage();
        Assertions.assertEquals(1,pageNumber);
    }
    @Test
    public void ShouldHasRightAmountOfPages()
    {
        Assertions.assertEquals(4,mock.getPagesCount());
    }

    @Test
    public void FirstPageShouldContainsContent()
    {
        var content1 = mock.getPageContent(0);
        Assertions.assertEquals(5,content1.size());
    }

    @Test
    public void LastPageShouldNotContainsContent()
    {
        var content1 = mock.getPageContent(4);
        Assertions.assertEquals(0,content1.size());
    }
}
