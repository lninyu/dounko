package me.lninyu.dounko;

import org.bukkit.*;
import org.bukkit.entity.BlockDisplay;
import org.bukkit.entity.EntityType;
import org.bukkit.util.Transformation;
import org.jetbrains.annotations.NotNull;
import org.joml.Matrix3f;
import org.joml.Quaternionf;
import org.joml.Vector3f;

public class Unko {
	private final BlockDisplay[] unko = new BlockDisplay[3];

	public Unko(Location location, Material material) {
		unko[0] = mkUnkoLayer(location, material, new float[]{0.5000f,0.0000f,0.0000f,-0.2500f,0.0000f,0.1250f,0.0000f,0.0000f,0.0000f,0.0000f,0.5000f,-0.2500f,0.0000f,0.0000f,0.0000f,1.0000f});
		unko[1] = mkUnkoLayer(location, material, new float[]{0.3750f,0.0000f,0.0000f,-0.1875f,0.0000f,0.1250f,0.0000f,0.1250f,0.0000f,0.0000f,0.3750f,-0.1875f,0.0000f,0.0000f,0.0000f,1.0000f});
		unko[2] = mkUnkoLayer(location, material, new float[]{0.2500f,0.0000f,0.0000f,-0.1250f,0.0000f,0.1250f,0.0000f,0.2500f,0.0000f,0.0000f,0.2500f,-0.1250f,0.0000f,0.0000f,0.0000f,1.0000f});
	}

	private static BlockDisplay mkUnkoLayer(Location location, Material material, float @NotNull [] matrix) {
		final var layer = (BlockDisplay) location.getWorld().spawnEntity(location, EntityType.BLOCK_DISPLAY);
		layer.setBlock(material.createBlockData());
		layer.setTransformation(matrixToTransformation(matrix));
		return layer;
	}

	private static @NotNull Transformation matrixToTransformation(float @NotNull [] matrix) {
		final var translation = new Vector3f(matrix[3], matrix[7], matrix[11]);
		final var scaleX = (float) Math.sqrt(matrix[0] * matrix[0] + matrix[4] * matrix[4] + matrix[8] * matrix[8]);
		final var scaleY = (float) Math.sqrt(matrix[1] * matrix[1] + matrix[5] * matrix[5] + matrix[9] * matrix[9]);
		final var scaleZ = (float) Math.sqrt(matrix[2] * matrix[2] + matrix[6] * matrix[6] + matrix[10] * matrix[10]);
		final var scale = new Vector3f(scaleX, scaleY, scaleZ);
		final var rotation = new Quaternionf().setFromNormalized(new Matrix3f(
			matrix[0]/scaleX, matrix[1]/scaleY, matrix[2]/scaleZ,
			matrix[4]/scaleX, matrix[5]/scaleY, matrix[6]/scaleZ,
			matrix[8]/scaleX, matrix[9]/scaleY, matrix[10]/scaleZ
		));

		return matrix.length == 16
			? new Transformation(translation, rotation, scale, new Quaternionf())
			: new Transformation(new Vector3f(), new Quaternionf(), new Vector3f(1), new Quaternionf());
	}

	public void kill() {
		unko[0].getWorld().spawnParticle(Particle.BLOCK, unko[0].getLocation(), 10, Material.DIRT.createBlockData());
		for (var layer : unko) if (layer != null && layer.isValid()) layer.remove();
	}
}
