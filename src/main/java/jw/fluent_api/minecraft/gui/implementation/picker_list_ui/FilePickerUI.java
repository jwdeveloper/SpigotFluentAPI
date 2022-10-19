package jw.fluent_api.minecraft.gui.implementation.picker_list_ui;

import jw.fluent_api.utilites.files.FileUtility;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.ChatColor;
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
            var files = getFolderFilesName();
            displayLog("Loaded "+files.size()+" files from path "+path, ChatColor.GREEN);
            setContentButtons(files, (data, button) ->
            {
                button.setMaterial(Material.PAPER);
                button.setTitle(data);
                button.setDataContext(data);
            });
            refresh();
        });
    }
    public void setExtensions(String... extensions) {
        this.extensions = extensions;
    }

    public final ArrayList<String> getFolderFilesName() {
        if (extensions == null)
            return FileUtility.getFolderFilesName(path);
        else
            return FileUtility.getFolderFilesName(path, extensions);
    }
}
