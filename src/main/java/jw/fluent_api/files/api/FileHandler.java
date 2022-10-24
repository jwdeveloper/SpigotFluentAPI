package jw.fluent_api.files.api;

import java.io.IOException;

public interface FileHandler
{
  public void load() throws IllegalAccessException, InstantiationException, IOException;

  public void save();

  public void addObject(Object object);

  public void removeObject(Object object);
}
