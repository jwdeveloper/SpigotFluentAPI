package jw.fluent_api.database.api.query_builder.update_builder;

public interface UpdateBuilder
{
     UpdateConditionsQuery table(Class<?> tableClass);

     UpdateConditionsQuery table(String table);

}
