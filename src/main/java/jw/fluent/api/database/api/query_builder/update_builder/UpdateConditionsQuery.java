package jw.fluent.api.database.api.query_builder.update_builder;

import jw.fluent.api.database.api.query_builder.where_builders.WhereBuilder;
import jw.fluent.api.database.api.query_builder.where_builders.WhereBuilderBridge;
import jw.fluent.api.database.api.query_abstract.AbstractQuery;

import java.util.HashMap;

public interface UpdateConditionsQuery extends AbstractQuery, WhereBuilderBridge {

    UpdateConditionsQuery set(String column, Object value);

    UpdateConditionsQuery set(HashMap<String,Object> values);

    WhereBuilder where();
}
