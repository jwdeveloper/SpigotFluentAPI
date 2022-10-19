package jw.spigot_fluent_api.desing_patterns.dependecy_injection.api.models;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Getter
public class ContainerConfiguration
{
   private List<RegistrationInfo> registrations = new ArrayList<>();
   private Set<Class<?>> registerdTypes = new HashSet<>();
   private EventsDto events = new EventsDto(new ArrayList<>(),new ArrayList<>());

   @Setter
   private boolean disableAutoRegistration;

   public void addRegistration(RegistrationInfo info)
   {
      registrations.add(info);
   }
}
