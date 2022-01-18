package jw.spigot_fluent_api.data;

import java.util.Collection;
import java.util.List;

public interface DataHandler
{
     void addSaveableObject(Collection<Saveable> objects);


     void addYmlObjects(Collection<Object> objects);


     void addJsonObjects(Collection<Object> objects);
}
