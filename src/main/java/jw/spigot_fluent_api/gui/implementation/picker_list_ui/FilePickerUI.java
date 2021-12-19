package jw.spigot_fluent_api.gui.implementation.picker_list_ui;

import jw.spigot_fluent_api.gui.events.ButtonUIEvent;
import jw.spigot_fluent_api.gui.implementation.list_ui.ListUI;
import jw.spigot_fluent_api.utilites.files.FileUtility;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.Material;

import java.util.ArrayList;

@Setter
@Getter
public class FilePickerUI extends PickerUI<String> {

    private String path;

    public FilePickerUI(String name, int height) {
        super(name);
        onListOpen(player ->
        {
            setContentButtons(getFolderFilesName(), (data, button) ->
            {
                button.setMaterial(Material.PAPER);
                button.setTitle(data);
                button.setDataContext(data);
            });
        });
    }


    private ArrayList<String> getFolderFilesName() {
        return FileUtility.getFolderFilesName(path);
    }
}
