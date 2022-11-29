package jw.fluent_api.desing_patterns.dependecy_injection.api.models;

import jw.fluent_api.desing_patterns.dependecy_injection.api.events.ContainerEvents;
import jw.fluent_api.desing_patterns.dependecy_injection.api.events.events.OnInjectionEvent;
import jw.fluent_api.desing_patterns.dependecy_injection.api.events.events.OnRegistrationEvent;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.Function;

@Getter
public class ContainerConfiguration
{
   private List<RegistrationInfo> registrations = new ArrayList<>();
   private Set<Class<?>> registerdTypes = new HashSet<>();
   private List<ContainerEvents> events = new ArrayList<>();
   private List<RegistrationInfo> registerAsList = new ArrayList<>();
   @Setter
   private boolean disableAutoRegistration;

   public void addRegistration(RegistrationInfo info)
   {
      registrations.add(info);
   }

   public void addRegistration(List<RegistrationInfo> info)
   {
      registrations.addAll(info);
   }

   public void onInjection(Function<OnInjectionEvent,Object> event)
   {
      events.add(new ContainerEvents() {
         @Override
         public boolean OnRegistration(OnRegistrationEvent e) {
            return true;
         }

         @Override
         public Object OnInjection(OnInjectionEvent e) throws Exception {
            return event.apply(e);
         }
      });
   }

   public void onRegistration(Function<OnRegistrationEvent,Boolean> event)
   {
      events.add(new ContainerEvents() {
         @Override
         public boolean OnRegistration(OnRegistrationEvent e) {
            return event.apply(e);
         }
         @Override
         public Object OnInjection(OnInjectionEvent event) throws Exception {
            return event.result();
         }

      });
   }
}
