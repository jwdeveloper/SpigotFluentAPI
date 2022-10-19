package jw.fluent_api.utilites.pagination;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class Pagination<T> {
    private Collection<T> content;
    private int currentPage;
    private List<T> pageContent;
    private final int maxContentOnPage;

    public Pagination(int maxContentOnPage) {
        this.maxContentOnPage = maxContentOnPage;
        setContent(new ArrayList<>());
    }

    public Pagination(Collection<T> content, int maxContentOnPage) {
        this(maxContentOnPage);
        setContent(content);
    }

    public void setContent(Collection<T> content) {
        this.content = content;
    }

    public List<T> getPageContent(int pageNumber)
    {
        var result  = new ArrayList<T>();
        if (pageNumber > getPagesCount())
            currentPage = getPagesCount();
        else
            currentPage = Math.max(pageNumber, 0);

        T[] allContent = (T[]) content.stream().toArray();
        for (int i = getCurrentPage() * getMaxContentOnPage(); i < getCurrentPage() * getMaxContentOnPage() + getMaxContentOnPage(); i++) {
            if (i >= allContent.length)
                break;
            result.add(allContent[i]);
        }
        return result;
    }

    public Collection<T> nextPage() {
        currentPage++;
        return getPageContent(currentPage);
    }

    public Collection<T> backPage() {
        currentPage--;
        return getPageContent(currentPage);
    }

    public Collection<T> setPage(int page)
    {
        page =  Math.min(currentPage , getPagesCount()-1);
        page = Math.max(page,0);
        currentPage = page;
        return getPageContent(page);
    }

    public List<T> getCurrentPageContent() {
        return getPageContent(currentPage);
    }

    public int getMaxContentOnPage() {
        return maxContentOnPage;
    }

    public int getCurrentPage() {
        return currentPage;
    }

    public int getPagesCount() {
        return (int) Math.ceil(content.size() / (double) maxContentOnPage);
    }

    public boolean canNextPage()
    {
        return currentPage < getPagesCount()-1;
    }

    public boolean canBackPage() {
        return currentPage > 0;
    }
}
