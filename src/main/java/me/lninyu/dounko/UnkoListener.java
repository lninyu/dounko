package me.lninyu.dounko;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerToggleSneakEvent;
import org.bukkit.plugin.java.JavaPlugin;

public final class UnkoListener implements Listener {
	private final UnkoManager unkoManager;
    private final JavaPlugin plugin;

    public UnkoListener(JavaPlugin plugin, UnkoManager unkoManager) {
		this.plugin = plugin;
		this.unkoManager = unkoManager;

	    Bukkit.getScheduler().runTaskTimer(plugin, unkoManager::tick, 0, 1);
    }

	@EventHandler
	public void onPlayerToggleSneak(PlayerToggleSneakEvent event) {
		var player = event.getPlayer();
		if (!player.isSneaking()) return;

		unkoManager.spawnUnko(new Location(player.getWorld(), player.getX(), player.getY(), player.getZ()), 100);
	}
}
