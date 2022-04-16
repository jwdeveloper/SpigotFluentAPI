package jw.spigot_fluent_api.data.interfaces;

public interface FileHandler
{
  public void load();

  public void save();

  public void addObject(Object object);

  public void removeObject(Object object);
}
