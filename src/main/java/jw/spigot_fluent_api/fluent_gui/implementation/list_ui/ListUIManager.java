package jw.spigot_fluent_api.fluent_gui.implementation.list_ui;

import jw.spigot_fluent_api.fluent_gui.button.ButtonUI;
import jw.spigot_fluent_api.fluent_gui.implementation.list_ui.content_manger.ButtonUIMapper;
import jw.spigot_fluent_api.fluent_gui.implementation.list_ui.content_manger.ContentManager;
import jw.spigot_fluent_api.fluent_gui.implementation.list_ui.content_manger.FilterContentEvent;
import jw.spigot_fluent_api.fluent_message.MessageBuilder;
import org.bukkit.ChatColor;

import java.util.ArrayList;
import java.util.List;

public class ListUIManager<T>
{
    private final ContentManager<T> contentManager;
    private final ListUI<T> listUI;
    private List<T> realContent;

   public ListUIManager(ListUI<T> listUI)
   {
       this.listUI = listUI;
       contentManager = new ContentManager<>(listUI.getHeight(),listUI.getWidth());
       realContent = new ArrayList<>();
   }

   public void setButtonFormatter(List<T> data, ButtonUIMapper<T> buttonMapper)
   {
       realContent = data;
       contentManager.setContent(realContent);
       contentManager.setButtonMapper(buttonMapper);
   }

   public void applyFilters()
   {
       var filtered =  contentManager.applyFilters(realContent);
       contentManager.setContent(filtered);
   }
    public void addFilter(FilterContentEvent<T> filterContentEvent)
    {
        contentManager.addFilter(filterContentEvent);
    }

    public void removeFilter(FilterContentEvent<T> filterContentEvent)
    {
        contentManager.removeFilter(filterContentEvent);
        var filtered =  contentManager.applyFilters(realContent);
        contentManager.setContent(filtered);
    }

   public void resetFilter()
   {
       contentManager.resetFilter();
       contentManager.setContent(realContent);
   }
    public ButtonUI[] getButtons()
    {
      return contentManager.getButtons();
    }

    public String pageDescription()
    {
        var pages = contentManager.getPagesCount() == 0? 1 : contentManager.getPagesCount();
        var page = (contentManager.getCurrentPage()+1)+"/"+(pages);
        var description  = new MessageBuilder();
        if(listUI.getListTitle().length() != 0)
        {
            description.text(listUI.getListTitle());
        }
        else
        {
            description.inBrackets(listUI.getName());
        }

        return description.space().color(ChatColor.WHITE).inBrackets(page).toString();
    }

    public void nextPage()
    {
        if(!contentManager.canNextPage())
        {
           return;
        }
        contentManager.nextPage();
        listUI.refreshContent();
    }

    public void lastPage()
    {
        if(!contentManager.canBackPage())
        {
            return;
        }
        contentManager.backPage();
        listUI.refreshContent();
    }




    public boolean isContentButton(ButtonUI buttonUI)
    {
        return contentManager.isContentButton(buttonUI);
    }
}
