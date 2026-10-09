package fr.dianox.hawn.paper;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.generator.ChunkGenerator;
import org.bukkit.generator.WorldInfo;

import java.util.Random;

/**
 * The void world of Hawn with the generator API of 1.17.1+ (Spigot and Paper): nothing but one bedrock block
 * under the spawn. The old generateChunkData is deprecated since 1.17.1.
 */
public class ModernVoidGenerator extends ChunkGenerator {

	@Override
	public void generateSurface(WorldInfo info, Random random, int chunkX, int chunkZ, ChunkData data) {
		if (chunkX == 0 && chunkZ == 0 && 63 >= data.getMinHeight() && 63 < data.getMaxHeight()) {
			data.setBlock(0, 63, 0, Material.BEDROCK);
		}
	}

	@Override
	public boolean shouldGenerateNoise() {
		return false;
	}

	@Override
	public boolean shouldGenerateSurface() {
		return false;
	}

	@Override
	public boolean shouldGenerateCaves() {
		return false;
	}

	@Override
	public boolean shouldGenerateDecorations() {
		return false;
	}

	@Override
	public boolean shouldGenerateMobs() {
		return false;
	}

	@Override
	public boolean shouldGenerateStructures() {
		return false;
	}

	@Override
	public Location getFixedSpawnLocation(World world, Random random) {
		return new Location(world, 0.5D, 64.0D, 0.5D);
	}
}
