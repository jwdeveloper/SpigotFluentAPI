package jw.spigot_fluent_api.utilites.files.yml.api.models;

import lombok.Data;

import java.io.File;

@Data
public class FileStatus
{
    private File file;
    private boolean alreadyCreated = false;


    public FileStatus(File file)
    {
        this.file = file;
    }
}
