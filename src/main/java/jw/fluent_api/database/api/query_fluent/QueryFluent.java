package jw.fluent_api.database.api.query_fluent;

import jw.fluent_api.database.api.query_abstract.AbstractQuery;

import java.util.List;
import java.util.Optional;

public interface QueryFluent<T> extends AbstractQuery {
    List<T> toList();

    List<T> toList(int limit);

    Optional<T> first();
}
