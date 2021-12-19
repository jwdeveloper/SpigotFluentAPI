package jw.spigot_fluent_api.gui.implementation.picker_list_ui;

import jw.spigot_fluent_api.utilites.files.FileUtility;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.Material;

import java.util.ArrayList;

@Setter
@Getter
public class FilePickerUI extends PickerUI<String> {

    private String path = "";
    private String[] extensions;

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


    public void setExtensions(String... extensions) {
        this.extensions = extensions;
    }

    private ArrayList<String> getFolderFilesName() {
        if (extensions == null)
            return FileUtility.getFolderFilesName(path);
        else
            return FileUtility.getFolderFilesName(path, extensions);
    }
}
