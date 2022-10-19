package jw.spigot_fluent_api.database.mysql_db.factories;

import jw.spigot_fluent_api.database.mysql_db.models.SqlDbContext;
import jw.spigot_fluent_api.database.mysql_db.models.SqlTable;
import jw.spigot_fluent_api.desing_patterns.dependecy_injection.FluentInjection;
import jw.spigot_fluent_api.desing_patterns.dependecy_injection.api.models.RegistrationInfo;
import jw.spigot_fluent_api.desing_patterns.dependecy_injection.api.enums.LifeTime;
import jw.spigot_fluent_api.desing_patterns.dependecy_injection.api.enums.RegistrationType;
import lombok.SneakyThrows;

import java.lang.reflect.ParameterizedType;

public class SqlDbContextFactory
{
    @SneakyThrows
    public static  <T extends SqlDbContext> T getDbContext(Class<T> contextType)
    {
        var registrationInfo = new RegistrationInfo(null,
                contextType,
                null,
                LifeTime.SINGLETON,
                RegistrationType.OnlyImpl);
        FluentInjection.getContainer().register(registrationInfo);
        var context = (SqlDbContext)FluentInjection.findInjection(contextType);
        for (var field : contextType.getDeclaredFields()) {
            field.setAccessible(true);
            var value = field.get(context);
            if (value != null)
                continue;

            var type = field.getType();
            if (!type.isInterface()) {
                continue;
            }


            var genericType = (ParameterizedType)field.getGenericType();
            var genericTypeArg = genericType.getActualTypeArguments()[0];
            var obj = new SqlTable((Class)genericTypeArg);
            context.tables.add(obj);
            field.set(context,obj);
        }
        return (T)context;
    }
}
