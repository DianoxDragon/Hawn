package fr.dianox.hawn.modules.world.generator;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.generator.ChunkGenerator;

import java.util.Random;

public class VoidGenerator extends ChunkGenerator {

	@Override
	public final ChunkData generateChunkData(World world, Random random, int ChunkX, int ChunkZ, BiomeGrid biome) {
		ChunkData chunkData = createChunkData(world);

		if (0 >= ChunkX << 4 && 0 < ChunkX + 1 << 4 && 0 >= ChunkZ << 4 && 0 < ChunkZ + 1 << 4) {
			chunkData.setBlock(0, 63, 0, Material.BEDROCK);
		}

		return chunkData;
	}

	public final Location getFixedSpawnLocation(World world, Random random) {
		return new Location(world, 0.0D, 64.0D, 0.0D);
	}

}
