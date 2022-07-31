package jw.spigot_fluent_api.data.interfaces;

import java.io.IOException;

public interface FileHandler
{
  public void load() throws IllegalAccessException, InstantiationException, IOException;

  public void save();

  public void addObject(Object object);

  public void removeObject(Object object);
}
