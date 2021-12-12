package jw.spigot_fluent_api.utilites.pagination;

import java.util.ArrayList;
import java.util.List;

public class Pagination<T>
{
    private List<T> content;
    private int pages;
    private int currentPage;
    private List<T> pageContent;
    private final int maxContentOnPage;

    public Pagination(int maxContentOnPage)
    {
        this.maxContentOnPage = maxContentOnPage;
        setContent(new ArrayList<>());
    }
    public Pagination(List<T> content, int maxContentOnPage)
    {
        this(maxContentOnPage);
        setContent(content);
    }

    public void setContent(List<T> content)
    {
        this.content = content;
        this.pages = (int)Math.ceil(content.size()/(double) maxContentOnPage);
        this.pageContent = new ArrayList<>();
    }

    public List<T> getPageContent(int pageNumber)
    {
        if(pageNumber>pages)
            currentPage = pages;
        else if(pageNumber<0)
            currentPage =0;
        else
            currentPage = pageNumber;

        pageContent.clear();
        for(int i = currentPage * maxContentOnPage;i<currentPage*maxContentOnPage + maxContentOnPage;i++)
        {
            if(i < content.size())
            {
                pageContent.add(content.get(i));
            }
            else
            {
                break;
            }
        }
        return pageContent;
    }
    public List<T> nextPage()
    {
        currentPage++;
        return getPageContent(currentPage);
    }
    public List<T> backPage()
    {
        currentPage--;
        return getPageContent(currentPage);
    }

    public int getMaxContentOnPage()
    {
        return maxContentOnPage;
    }
    public List<T> getCurrentPageContent()
    {
        return getPageContent(currentPage);
    }
    public int getCurrentPage()
    {
        return currentPage;
    }
    public int getPages()
    {
        return pages;
    }
    public boolean canNextPage()
    {
        return currentPage + 1 <=pages;
    }
    public boolean canBackPage()
    {
        return currentPage - 1 >0;
    }
}
