package jw.fluent_api.desing_patterns.dependecy_injection.api.containers;

import jw.fluent_api.desing_patterns.dependecy_injection.api.search.ContainerSearch;
import jw.fluent_api.desing_patterns.dependecy_injection.api.search.SearchAgent;

import java.lang.annotation.Annotation;
import java.util.Collection;

public interface FluentContainer extends Container, ContainerSearch {

}
