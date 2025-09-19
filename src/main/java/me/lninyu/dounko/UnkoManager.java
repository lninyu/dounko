package me.lninyu.dounko;

import org.bukkit.Location;
import org.bukkit.Material;

import java.util.HashMap;
import java.util.Map;

public final class UnkoManager {
	private final Map<Unko, Integer> unkoList = new HashMap<>();

	public void spawnUnko(Location location, int lifetime) {
		this.unkoList.put(new Unko(location, Material.JUNGLE_WOOD), Math.max(lifetime, 0));
	}

	public void tick() {
		var iterator = unkoList.entrySet().iterator();

		while (iterator.hasNext()) {
			var entry = iterator.next();
			int lifetime = entry.getValue() - 1;

			if (lifetime < 0) {
				entry.getKey().kill();
				iterator.remove();
			} else {
				entry.setValue(lifetime);
			}
		}
	}

	public void killAll() {
		for (var unko : unkoList.keySet()) unko.kill();
		unkoList.clear();
	}
}
