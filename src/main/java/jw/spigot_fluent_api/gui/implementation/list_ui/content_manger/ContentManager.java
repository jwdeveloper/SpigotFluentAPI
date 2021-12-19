package jw.spigot_fluent_api.gui.implementation.list_ui.content_manger;

import jw.spigot_fluent_api.gui.button.ButtonUI;
import jw.spigot_fluent_api.utilites.pagination.Pagination;
import org.bukkit.Material;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ContentManager<T> extends Pagination<T> {

    private final ButtonUI[] buttons;
    private ButtonUIMapper<T> buttonMapper = (a,b)->{};
    private final List<FilterContentEvent<T>> filters;
    private int height;
    private int width;

    public ContentManager(int height, int width)
    {
        super((height-2)*(width-2));
        this.height =height-2;
        this.width = width-2;
        filters = new ArrayList<>();
        buttons = createPlaceHolderButtons();
    }

    public boolean isContentButton(ButtonUI buttonUI)
    {
      return Arrays.stream(buttons).toList().contains(buttonUI);
    }

    public ButtonUI[] getButtons()
    {
        final List<T> data = getCurrentPageContent();
        T tempData;
        ButtonUI button;
        for(int i = 0; i< buttons.length; i++)
        {
            button = buttons[i];
            if(i < data.size())
            {
                tempData = data.get(i);
                button.setActive(true);
                buttonMapper.mapButton(tempData,button);
            }
            else
            {
                button.setActive(false);
            }
        }
        return buttons;
    }

    public void addFilter(FilterContentEvent<T> filterContentEvent)
    {
       this.filters.add(filterContentEvent);
    }

    public void resetFilter()
    {
        filters.clear();
    }

    public void removeFilter(FilterContentEvent<T> filterContentEvent)
    {
        this.filters.remove(filterContentEvent);
    }

    public List<T> applyFilters(List<T> input)
    {
        if (filters.size() == 0)
        {
            return input;
        }
        List<T> result = new ArrayList<>();
        for (T item : input)
        {
            for (var filter : filters) {
                if (filter.execute(item))
                {
                    result.add(item);
                }
            }
        }
        return  result;
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
        for(int i=0;i<height;i++)
        {
            for(int j=0;j<width;j++)
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

