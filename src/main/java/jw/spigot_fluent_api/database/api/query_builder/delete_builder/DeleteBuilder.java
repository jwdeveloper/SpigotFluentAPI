package jw.spigot_fluent_api.database.api.query_builder.delete_builder;

import jw.spigot_fluent_api.database.api.query_builder.where_builders.WhereBuilderBridge;

public interface DeleteBuilder 
{
     WhereBuilderBridge from(Class<?> tableClass);

     WhereBuilderBridge from(String table);

}
