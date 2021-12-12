package jw.spigot_fluent_api.gui_v2.list_ui.content_manger;

import jw.spigot_fluent_api.gui_v2.button.ButtonUI;
import jw.spigot_fluent_api.utilites.pagination.Pagination;
import org.bukkit.Material;

import java.util.Arrays;
import java.util.List;

public class ContentManager<T> extends Pagination<T> {

    private final ButtonUI[] buttons;
    private ButtonUIMapper<T> buttonMapper = (a,b)->{};
    private int height;
    private int width;

    public ContentManager(int height, int width)
    {
        super((height-2)*(width-2));
        this.height =height;
        this.width = width;
        buttons = createPlaceHolderButtons();
    }

    public boolean isContentButton(ButtonUI buttonUI)
    {
      return Arrays.stream(buttons).toList().contains(buttonUI);
    }

    public ButtonUI[] getButtons()
    {
        final List<T> currentData = getCurrentPageContent();
        T data;
        ButtonUI button;
        for(int i = 0; i< buttons.length; i++)
        {
            button = buttons[i];
            if(i < currentData.size())
            {
                data = currentData.get(i);
                button.setActive(true);
                buttonMapper.mapButton(data,button);
            }
            else
            {
                button.setActive(false);
            }
        }
        return buttons;
    }

    public void setButtonMapper(ButtonUIMapper<T> buttonMapper)
    {
        this.buttonMapper = buttonMapper;
    }

    private ButtonUI[] createPlaceHolderButtons()
    {
        ButtonUI[] result = new ButtonUI[getMaxContentOnPage()];
        ButtonUI button;
        int index =0;
        for(int i=0;i<height-2;i++)
        {
            for(int j=0;j<width-2;j++)
            {
                button = ButtonUI.builder().setMaterial(Material.PAPER)
                                           .setTitle("Place holder")
                                           .setLocation(i+1,j+1)
                                           .build();
                result[index] = button;
                index++;
            }
        }
        return result;
    }
}

