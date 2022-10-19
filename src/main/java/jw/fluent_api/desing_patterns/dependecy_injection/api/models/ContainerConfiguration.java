package jw.fluent_api.desing_patterns.dependecy_injection.api.models;

import jw.fluent_api.desing_patterns.dependecy_injection.api.events.ContainerEvents;
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
   private List<ContainerEvents> events = new ArrayList<>();

   @Setter
   private boolean disableAutoRegistration;

   public void addRegistration(RegistrationInfo info)
   {
      registrations.add(info);
   }
}
