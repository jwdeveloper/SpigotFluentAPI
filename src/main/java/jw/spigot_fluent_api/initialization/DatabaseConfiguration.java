package jw.spigot_fluent_api.initialization;

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
   public String databaseAdress;
   public String user;
   public String password;
}
