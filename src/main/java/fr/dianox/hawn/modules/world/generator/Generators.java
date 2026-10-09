package fr.dianox.hawn.modules.world.generator;

import org.bukkit.generator.ChunkGenerator;

/**
 * The void generator of Hawn ("hvg"): the one of the 1.17.1+ API when the server has it, the old one before.
 */
public final class Generators {

	private Generators() {}

	public static ChunkGenerator voidGenerator() {
		try {
			Class.forName("org.bukkit.generator.WorldInfo");
			return (ChunkGenerator) Class.forName("fr.dianox.hawn.paper.ModernVoidGenerator").getDeclaredConstructor().newInstance();
		} catch (Throwable e) {
			return new VoidGenerator();
		}
	}
}
