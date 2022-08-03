package jw.spigot_fluent_api.web_socket;

import jw.spigot_fluent_api.desing_patterns.observer.Observer;
import jw.spigot_fluent_api.web_socket.resolver.TypeResolver;
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
