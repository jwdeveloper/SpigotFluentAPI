package jw.spigot_fluent_api.data;

import java.util.List;

public interface DataHandler
{
     void addSaveableObject(List<Saveable> objects);


     void addYmlObjects(List<Object> objects);


     void addJsonObjects(List<Object> objects);
}
