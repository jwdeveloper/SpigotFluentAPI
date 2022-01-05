package jw.spigot_fluent_api.fluent_plugin;

public class DatabaseConfiguration
{
    public enum Database
    {
        MongoDb,
        MySql,
        FireBase,
    }
   public Database databaseType;
   public String databaseName;
   public String databaseAddres;
   public String user;
   public String password;
}
