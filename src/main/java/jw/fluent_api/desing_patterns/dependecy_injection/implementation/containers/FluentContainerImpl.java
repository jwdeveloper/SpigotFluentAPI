package jw.fluent_api.desing_patterns.dependecy_injection.implementation.containers;

import jw.fluent_api.desing_patterns.dependecy_injection.api.containers.Container;
import jw.fluent_api.desing_patterns.dependecy_injection.api.containers.FluentContainer;
import jw.fluent_api.desing_patterns.dependecy_injection.api.models.RegistrationInfo;
import jw.fluent_api.desing_patterns.dependecy_injection.api.provider.InstanceProvider;
import jw.fluent_api.desing_patterns.dependecy_injection.api.events.EventHandler;
import jw.fluent_api.desing_patterns.dependecy_injection.api.factory.InjectionInfoFactory;
import jw.fluent_api.desing_patterns.dependecy_injection.api.models.InjectionInfo;
import jw.fluent_api.desing_patterns.dependecy_injection.api.search.SearchAgent;
import jw.fluent_api.logger.api.SimpleLogger;
import jw.fluent_api.utilites.java.ObjectUtility;
import lombok.SneakyThrows;

import java.lang.annotation.Annotation;
import java.util.*;

public class FluentContainerImpl extends DefaultContainer implements FluentContainer {

    public FluentContainerImpl(
            SearchAgent searchAgent,
            InstanceProvider instaneProvider,
            EventHandler eventHandler,
            SimpleLogger logger,
            InjectionInfoFactory injectionInfoFactory,
            List<RegistrationInfo> registrationInfos) {
        super(searchAgent, instaneProvider, eventHandler, logger, injectionInfoFactory, registrationInfos);
    }

}
