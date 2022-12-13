package jw.fluent.api.spigot.gui.armorstand_gui.implementation.gui.interactive;

import jw.fluent.api.spigot.gui.armorstand_gui.api.button.enums.StandButtonEventType;
import jw.fluent.api.spigot.gui.armorstand_gui.api.button.events.StandButtonEvent;
import jw.fluent.api.spigot.gui.armorstand_gui.implementation.button.StandButton;
import jw.fluent.api.spigot.particles.api.enums.ParticleDisplayMode;
import jw.fluent.api.utilites.math.collistions.HitBox;
import jw.fluent.plugin.implementation.FluentApi;
import jw.fluent.plugin.implementation.modules.files.logger.FluentLogger;
import jw.fluent.plugin.implementation.modules.tasks.FluentTasks;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Player;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.Vector;

public class InteractiveButton {
    private final StandButton standButton;
    private Player player;
    private ArmorStand armorStand;

    private ArmorStand armorStandName;

    private InteractiveHitBox hitBox;

    private HitBoxDisplay hitBoxDisplay;

    public InteractiveButton(StandButton standButton, Player player) {
        this.standButton = standButton;
        this.player = player;
    }

    public void flyToPlayer(Player player) {
        FluentApi.tasks().taskTimer(3, (iteration, task) ->
                {
                    updateLocation(new Vector(0, -1, -1));
                }).onStop(simpleTaskTimer ->
                {
                    updateLocation(player.getLocation());
                    hitBoxDisplay.hide();
                })
                .stopAfterIterations(5).run();
    }


    public void handleLeftPlayerClick(Player player)
    {
       standButton.onLeftClick(new StandButtonEvent(player, standButton, StandButtonEventType.LEFT_CLICK));
    }
    public void handleRightPlayerClick(Player player)
    {
        standButton.onRightClick(new StandButtonEvent(player, standButton, StandButtonEventType.RIGHT_CLICK));
    }

    boolean hasMoved = false;

    public void playerLooking()
    {
        if(hasMoved)
        {
            return;
        }
        hasMoved = true;
        updateLocation(new Vector(0, 0, -2));
        armorStandName.setCustomName(ChatColor.AQUA+standButton.getTitle());
        armorStand.setHelmet(new ItemStack(Material.DIAMOND_BLOCK));
    }

    public void playerNotLooking()
    {
        hasMoved = false;
        updateLocation(new Vector(0, 0, 1));
        armorStandName.setCustomName(ChatColor.GRAY+standButton.getTitle());
        armorStand.setHelmet(standButton.getIcon());
    }

    public boolean isPlayerLooking(Player player) {
        var ray = 1;
        var loc = player.getEyeLocation();
        var output = loc.multiply(ray);
        var result = hitBox.isCollider(output, 10);
        return result;
    }
    float rotation = -360;
    public void flyAwayFromPlayer(Player player) {
        armorStand = createArmorStand(player.getLocation());
        armorStandName = createArmorStand(player.getLocation());
        armorStandName.setCustomName(standButton.getTitle());
        armorStand.setHelmet(standButton.getIcon());
        hitBox = new InteractiveHitBox(player.getLocation(), standButton.getHitBox());
        hitBoxDisplay = new HitBoxDisplay(hitBox);

        FluentApi.tasks().taskTimer(3, (iteration, task) ->
                {
                    updateLocation(new Vector(0, 1, 1));
                }).stopAfterIterations(5)
                .onStop(simpleTaskTimer ->
                {
                    updateLocation(standButton.getOffset());
                    hitBoxDisplay.show(0.4f);
                    FluentApi.particles()
                            .startAfterSeconds(1)
                            .stopAfterSeconds(10)
                            .nextStep()
                            .withDisplayMode(ParticleDisplayMode.SINGLE_AT_ONCE)
                            .withFixedLocation(armorStand.getLocation())
                            .withParticle(Particle.DRIP_LAVA)
                            .nextStep()
                            .onParticle((particle, particleInvoker) ->
                            {
                                particle.setAmount(2);
                                particleInvoker.spawnParticle(particle);
                            })
                            .nextStep()
                            .buildAndStart();

                    FluentApi.tasks().taskTimer(1, (iteration, task) ->
                    {
                        armorStand.setHeadPose(armorStand.getHeadPose().add(0, 0.2, 0));
                    }).run();
                })
                .run();
    }

    private void updateLocation(Location location) {
        var loc = location.clone();
        hitBox.updateOrigin(loc);
        armorStand.teleport(loc);
        armorStandName.teleport(loc.clone().add(0,1,0));
    }

    private void updateLocation(Vector offset) {
        var location = armorStand.getLocation().clone();
        location.add(offset.getX(), offset.getY(), offset.getZ());
        hitBox.updateOrigin(location.clone().add(0,2f,0));
        hitBoxDisplay.show(0.4f);
        armorStand.teleport(location);
        armorStandName.teleport(location.clone().add(0,3,0));
    }

    private ArmorStand createArmorStand(Location location) {
        var armorStand = location.getWorld().spawn(location, ArmorStand.class);
        armorStand.setVisible(false);
        armorStand.setCollidable(false);
        armorStand.setGravity(false);
        armorStand.setAI(false);
        armorStand.setMarker(true);
        armorStand.setNoDamageTicks(0);
        armorStand.setInvulnerable(true);
        armorStand.setInvisible(true);
        armorStand.setCustomNameVisible(true);
        armorStand.setRemoveWhenFarAway(true);
        armorStand.addEquipmentLock(EquipmentSlot.HEAD, ArmorStand.LockType.REMOVING_OR_CHANGING);
        armorStand.setRotation(0, 0);
        return armorStand;
    }

}
