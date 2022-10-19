package jw.fluent_api.web_socket;

import jw.fluent_api.desing_patterns.observer.implementation.Observer;
import jw.fluent_api.web_socket.resolver.TypeResolver;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class PacketFieldWrapper
{
    private Observer<Object> observer;

    private TypeResolver resolver;
}
