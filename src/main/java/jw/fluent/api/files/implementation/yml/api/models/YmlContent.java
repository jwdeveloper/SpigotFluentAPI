package jw.fluent.api.files.implementation.yml.api.models;

import jw.fluent.plugin.implementation.modules.messages.FluentMessage;
import jw.fluent.api.files.implementation.yml.implementation.YmlModelFactory;
import jw.fluent.api.utilites.java.StringUtils;
import jw.fluent.plugin.implementation.modules.files.logger.FluentLogger;
import lombok.Data;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.inventory.ItemStack;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.List;

@Data
public class YmlContent
{
     private String name= StringUtils.EMPTY;

     private String path= StringUtils.EMPTY;

     private String description = StringUtils.EMPTY;

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
          if(value == null)
          {
               return null;
          }
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

          if (field.getType().isAssignableFrom(List.class))
          {
               var value = getListContent(section);
               field.setAccessible(true);
               field.set(object, value);
               field.setAccessible(false);
          }

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

//todo refactor
     public Object getListContent(ConfigurationSection section)
     {
          List result = new ArrayList<>();
          try
          {
               ParameterizedType arrayType = (ParameterizedType) field.getGenericType();
              //FluentLogger.LOGGER.info("List",  arrayType.getRawType().getTypeName());

               var memberType = arrayType.getActualTypeArguments()[0];
              // FluentLogger.LOGGER.info("ListGeneric",arrayType.getActualTypeArguments()[0].getTypeName());
               var listSection =section.getConfigurationSection(getFullPath());
               if(listSection == null)
               {
                    return result;
               }
               var membersYml =  listSection.getKeys(false);
               if(membersYml == null)
               {
                    return result;
               }

               var memberClass =Class.forName(memberType.getTypeName());
               var memberObject = memberClass.newInstance();
               var contents = new YmlModelFactory()
                       .createModel(memberObject)
                       .getContents();

               var methodAdd = result.getClass().getDeclaredMethod("add",Object.class);
               for(var child : membersYml)
               {
                    if(child.contains("-"))
                    {
                         var msg = FluentMessage.message()
                                 .text("Bad formatting of yaml section").space()
                                 .text(getFullPath()+" "+child,ChatColor.WHITE).space()
                                 .text("List members in config can not contains",ChatColor.YELLOW).space()
                                 .text(" - ",ChatColor.WHITE).space()
                                 .text("prefix, remove it and",ChatColor.YELLOW).space()
                                 .text("reload",ChatColor.WHITE).space()
                                 .text("server",ChatColor.YELLOW);
                         throw new Exception(msg.toString());
                    }
                    var temp =memberClass.newInstance();
                    var childPath =section
                            .getConfigurationSection(getFullPath()+"."+child)
                            .getKeys(false);
                    if(childPath == null)
                    {
                         continue;
                    }

                    for(var content : contents)
                    {
                        // FluentLogger.success("Name",content.getName());
                         if(!childPath.contains(content.getName()))
                         {
                              continue;
                         }
                         var path = getFullPath()+"."+child+"."+content.getName();
                        // FluentLogger.success("fullpath",path);
                         var value = section.get(path);

                         content.getField().setAccessible(true);
                         content.getField().set(temp,value);
                    }

                    methodAdd.invoke(result,temp);
               }
               return result;
          }
          catch (Exception e)
          {
               FluentLogger.LOGGER.error("Could not load list section",e);
          }
          return result;
     }
}
