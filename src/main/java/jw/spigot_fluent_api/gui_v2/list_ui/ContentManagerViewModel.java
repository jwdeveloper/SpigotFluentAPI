package jw.spigot_fluent_api.gui_v2.list_ui;

import jw.spigot_fluent_api.gui_v2.button.ButtonUI;
import jw.spigot_fluent_api.gui_v2.list_ui.content_manger.ButtonUIMapper;
import jw.spigot_fluent_api.gui_v2.list_ui.content_manger.ContentManager;
import jw.spigot_fluent_api.utilites.messages.MessageBuilder;
import org.bukkit.ChatColor;

import java.util.List;

public class ContentManagerViewModel<T>
{
    private final ContentManager<T> content;
    private final ListUI<T> listUI;

   public ContentManagerViewModel(ListUI<T> listUI)
   {
       this.listUI = listUI;
       content = new ContentManager(listUI.getHeight(),listUI.getWidth());
   }


   public void setButtonFormatter(List<T>data, ButtonUIMapper<T> buttonMapper)
   {

       content.setContent(data);
       content.setButtonMapper(buttonMapper);
   }
    public ButtonUI[] getButtons()
    {
      return content.getButtons();
    }

    public String pageDescription()
    {
        var page = content.getCurrentPage()+"/"+content.getMaxContentOnPage();
        return new MessageBuilder().color(ChatColor.WHITE).inBrackets(page).toString();
    }


    public boolean isContentButton(ButtonUI buttonUI)
    {
        return content.isContentButton(buttonUI);
    }
}
