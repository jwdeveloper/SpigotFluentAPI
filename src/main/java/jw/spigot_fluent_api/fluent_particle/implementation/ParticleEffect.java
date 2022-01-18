package jw.spigot_fluent_api.fluent_particle.implementation;

import jw.spigot_fluent_api.fluent_plugin.FluentPlugin;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.scheduler.BukkitTask;

public class ParticleEffect {

    private final ParticleInvoker particleInvoker;
    private final ParticleSettings settings;
    private BukkitTask bukkitTask;
    private int time;


    public ParticleEffect(final ParticleSettings particleSettings) {
        this.settings = particleSettings;
        this.particleInvoker = new ParticleInvoker();
    }

    public void start() {
        stop();
        run();
    }

    public void followEntity(Entity entity) {
        this.settings.setEntityToTrack(entity);
    }

    public void setLocation(Location location) {
        this.settings.setLocation(location);
    }

    private void run() {
        bukkitTask = Bukkit.getScheduler().runTaskTimer(FluentPlugin.getPlugin(),
                () -> {
                    try {
                        if (time >= settings.getStopAfterTicks()) {
                            stop();
                            return;
                        }
                        var event = new ParticleEvent();
                        event.location = settings.getLocation().clone();
                        event.index = time % settings.getParticleCount();
                        event.particleColor = settings.getColor();
                        event.particle = settings.getParticle();
                        event.setParticleColor(settings.getColor());
                        event.time = time;
                        if (settings.getParticleDisplayMode() == ParticleDisplayMode.ALL_AT_THE_TIME) {
                            for (var i = 0; i < settings.getParticleCount(); i++) {
                                event.location = settings.getLocation().clone();
                                event.index = i;
                                settings.getOnParticleEvent().execute(event, particleInvoker);
                            }
                        } else {
                            event.index = time % settings.getParticleCount();
                            settings.getOnParticleEvent().execute(event, particleInvoker);
                        }

                        time++;
                    } catch (Exception e) {
                        FluentPlugin.logException("Error while running particleEffect", e);
                        bukkitTask.cancel();
                    }
                },
                settings.getStartAfterTicks(),
                settings.getTriggerEveryTicks());
    }


    public boolean isRunning() {
        return bukkitTask != null;
    }

    public void stop() {
        if (bukkitTask == null)
            return;
        bukkitTask.cancel();
        time = 0;
        settings.setOnStop(null);
    }
}
