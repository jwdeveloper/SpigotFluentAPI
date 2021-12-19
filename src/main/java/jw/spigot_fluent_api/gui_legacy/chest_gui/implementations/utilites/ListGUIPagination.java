package jw.spigot_fluent_api.gui_legacy.chest_gui.implementations.utilites;


import jw.spigot_fluent_api.gui_legacy.button.Button;
import jw.spigot_fluent_api.gui_legacy.chest_gui.implementations.button.ButtonMapper;
import jw.spigot_fluent_api.utilites.pagination.Pagination;
import org.bukkit.Material;

import java.util.List;

public class ListGUIPagination<T> extends Pagination<T>
{
    private final Button[] buttonList;
    private ButtonMapper<T> buttonMapper = this::mapButtons;

    public ListGUIPagination(int maxContentOnPage)
    {
        super(maxContentOnPage);
        buttonList = createTemplateButtons();

    }

    public Button[] getButtons()
    {
        final List<T> currentData = this.getCurrentPageContent();
        T data;
        Button button;
        for(int i=0;i<buttonList.length;i++)
        {
            button = buttonList[i];
            if(i < currentData.size())
            {
                data = currentData.get(i);
                button.setActive(true);
                this.buttonMapper.mapButton(data,button);
            }
            else
            {
                button.setActive(false);
            }
        }
        return buttonList;
    }


    private void mapButtons(T data,Button button)
    {

    }

    public void setButtonMapper(ButtonMapper<T> buttonMapper)
    {
        this.buttonMapper = buttonMapper;
    }

    private Button[] createTemplateButtons()
    {
        Button[] result = new Button[getMaxContentOnPage()];
        Button button;
        int height = (getMaxContentOnPage()/7);
        int width = 7;
        int index =0;
        for(int i=0;i<height;i++)
        {
            for(int j=0;j<width;j++)
            {
                button = new Button(Material.PAPER,"Place holder");
                button.setPosition(i+1,j+1);
                result[index] = button;
                index++;
            }
        }
        return result;
    }

}
