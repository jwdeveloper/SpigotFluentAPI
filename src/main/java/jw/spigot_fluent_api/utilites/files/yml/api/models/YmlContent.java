package jw.spigot_fluent_api.utilites.files.yml.api.models;

import jw.spigot_fluent_api.utilites.java.JavaUtils;
import lombok.Data;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.inventory.ItemStack;

import java.lang.reflect.Field;

@Data
public class YmlContent
{
     private String name= JavaUtils.EMPTY_STRING;

     private String path= JavaUtils.EMPTY_STRING;

     private String description = JavaUtils.EMPTY_STRING;

     private Object object;

     private Field field;

     private boolean required;

     public String getFullPath()
     {
          if(path.isEmpty())
          {
               return name;
          }
          return path+"."+name;
     }

     public Object getValue() throws IllegalAccessException {
          field.setAccessible(true);
          Object value = field.get(object);
          field.setAccessible(false);
          if (value.getClass().equals(Material.class)) {
               var material = (Material) value;
               value = material.name();
          }
          if (value.getClass().equals(ChatColor.class)) {
               var color = (ChatColor) value;
               value = color.name();
          }
          return value;
     }

     public void setValue(ConfigurationSection section) throws IllegalAccessException {
          var value = section.get(getFullPath());


          if (field.getType().equals(Material.class)) {
               value = Material.valueOf((String) value);
          }
          if (field.getType().equals(ChatColor.class)) {
               value = section.getColor(field.getName());
          }
          if (field.getType().equals(ItemStack.class)) {
               value = section.getItemStack(field.getName());
          }
          if (field.getType().getName().equalsIgnoreCase("double")) {
               value = Double.parseDouble(value.toString());
          }
          if (field.getType().getName().equalsIgnoreCase("float")) {
               value = Float.parseFloat(value.toString());
          }
          field.setAccessible(true);
          field.set(object, value);
          field.setAccessible(false);
     }
}
