package me.lninyu.dounko;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitTask;

public final class Dounko extends JavaPlugin {
	@Override
	public void onEnable() {
		// Plugin startup logic
		getServer().getPluginManager().registerEvents(new UnkoListener(this, new UnkoManager()), this);
	}

	@Override
	public void onDisable() {
		// Plugin shutdown logic
	}
}
