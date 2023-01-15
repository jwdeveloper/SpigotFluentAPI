/*
 * MIT License
 *
 * Copyright (c)  2023. jwdeveloper
 *
 *  Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 *  in the Software without restriction, including without limitation the rights
 *  to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 *  copies of the Software, and to permit persons to whom the Software is
 *  furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 *  IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 *  FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 *  LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package jw.fluent.api.temp;

import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitTask;

import java.util.HashMap;
import java.util.Map;

public class SegmentedHitboxPlugin extends JavaPlugin implements Listener {

  private final Map<Player, SegmentedHitbox> hitboxes;
  private BukkitTask tickerHandle;

  public SegmentedHitboxPlugin() {
    this.hitboxes = new HashMap<>();
  }

  @Override
  public void onEnable() {


    getServer().getPluginManager().registerEvents(this, this);

    for (Player p : Bukkit.getOnlinePlayers())
      createHitboxFor(p);

    tickerHandle = Bukkit.getScheduler().runTaskTimer(this, new Runnable() {

      private long time;

      @Override
      public void run() {
        time++;
        for (SegmentedHitbox hitbox : hitboxes.values())
          hitbox.tick(time);
      }

    }, 0, 0);
  }

  @Override
  public void onDisable() {
    if (tickerHandle != null)
      tickerHandle.cancel();

    for (Player p : Bukkit.getOnlinePlayers())
      destroyHitboxFor(p);
  }

  @EventHandler
  public void onInteract(PlayerInteractEvent e) {
    if (!(e.getAction() == Action.LEFT_CLICK_AIR || e.getAction() == Action.LEFT_CLICK_BLOCK))
      return;

    Player p = e.getPlayer();
    BulletResult result = Bullet.shoot(p, p.getEyeLocation(), 30, hitboxes);
    visualizeVector(result.getPath(), Color.RED);

    if (result.getTarget() != null && result.getSegment() != null)
      System.out.println("§a" + p.getName() + " shot " + result.getTarget().getName() + " into " + result.getSegment().name());
  }

  private void visualizeVector(IDrawableVector vector, Color color) {
    double length = vector.getLength();
    double stepSize = .1 / length;

    for (double multiplier = 0; multiplier <= 1; multiplier += stepSize) {
      spawnParticle(
        vector.getOrigin().getX() + (vector.getX() * multiplier),
        vector.getOrigin().getY() + (vector.getY() * multiplier),
        vector.getOrigin().getZ() + (vector.getZ() * multiplier),
        color
      );
    }
  }

  private void spawnParticle(double x, double y, double z, Color color) {
    Bukkit.getWorlds().get(0).spawnParticle(Particle.REDSTONE, x, y, z, 1, new Particle.DustOptions(color, 1));
  }

  @EventHandler
  public void onJoin(PlayerJoinEvent e) {
    createHitboxFor(e.getPlayer());
  }

  @EventHandler
  public void onQuit(PlayerQuitEvent e) {
    destroyHitboxFor(e.getPlayer());
  }

  private void destroyHitboxFor(Player p) {
    hitboxes.remove(p);
  }

  private void createHitboxFor(Player p) {
    hitboxes.put(p, new SegmentedHitbox(p));
  }
}
