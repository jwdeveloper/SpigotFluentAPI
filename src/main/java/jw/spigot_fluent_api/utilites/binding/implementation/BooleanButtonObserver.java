package jw.spigot_fluent_api.utilites.binding.implementation;
import jw.spigot_fluent_api.fluent_gui.button.button_observer.ButtonObserver;
import jw.spigot_fluent_api.utilites.binding.Observable;

public class BooleanButtonObserver
{
  public static ButtonObserver<Boolean> create(Observable<Boolean> observable)
  {
      return  ButtonObserver.<Boolean>builder()
              .withObserver(observable)
              .onClick(event ->
              {
                  event.getObserver().setValue(!event.getValue());
              })
              .onValueChange(event ->
              {
                  if(event.getValue())
                  {
                      event.getButton().setHighlighted(true);
                      event.getButton().setDescription("Enable");
                  }
                  else
                  {
                      event.getButton().setHighlighted(false);
                      event.getButton().setDescription("Disable");
                  }
              })
              .build();
  }
}
