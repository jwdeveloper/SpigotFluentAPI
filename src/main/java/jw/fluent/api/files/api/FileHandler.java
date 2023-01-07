package jw.fluent.api.files.api;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

public interface FileHandler
{
  public void load() throws IllegalAccessException, InstantiationException, IOException, ClassNotFoundException, InvocationTargetException, NoSuchMethodException;

  public void save();

  public void addObject(Object object);

  public void removeObject(Object object);
}
