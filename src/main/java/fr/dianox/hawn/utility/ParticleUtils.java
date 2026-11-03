package fr.dianox.hawn.utility;

import com.cryptomorin.xseries.particles.XParticle;
import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.configuration.ConfigurationSection;

import java.util.Optional;
import java.util.logging.Level;

/**
 * The particles written in the configuration files (since 1.5.6): a section with Particle, Amount, Offset, Speed and,
 * for DUST, Color ("255,0,255"). Legacy names are accepted too thanks to XSeries.
 */
public final class ParticleUtils {

	private ParticleUtils() {}

	/**
	 * Shows the particles of the section at this location, to every player nearby. Nothing if the particle is unknown.
	 */
	public static void spawn(Location loc, ConfigurationSection section, String path) {
		if (section == null || loc.getWorld() == null) {
			return;
		}

		Particle particle = particle(section.getString("Particle"), path + ".Particle");
		if (particle == null) {
			return;
		}

		int amount = section.getInt("Amount", 1);
		double offset = section.getDouble("Offset", 0);
		double speed = section.getDouble("Speed", 0);

		Class<?> data = particle.getDataType();
		if (data == Particle.DustOptions.class) {
			loc.getWorld().spawnParticle(particle, loc, amount, offset, offset, offset, speed,
					new Particle.DustOptions(color(section.getString("Color", "255,255,255")), 1));
		} else if (data == Void.class) {
			loc.getWorld().spawnParticle(particle, loc, amount, offset, offset, offset, speed);
		} else if (Debug.firstTime("particle data", path)) {
			Bukkit.getLogger().log(Level.WARNING, "Hawn | (Configuration error): The particle " + particle + " needs a block or an item, choose another one");
			Bukkit.getLogger().log(Level.WARNING, "Hawn | (Configuration error): Notes: " + path);
		}
	}

	private static Particle particle(String name, String path) {
		Optional<XParticle> xParticle = name == null ? Optional.empty() : XParticle.of(name);
		Particle particle = xParticle.map(XParticle::get).orElse(null);

		if (particle == null && Debug.firstTime("particle", name + " " + path)) {
			Bukkit.getLogger().log(Level.WARNING, "Hawn | (Configuration error): The particle " + name + " is not recognized, no particle is shown");
			Bukkit.getLogger().log(Level.WARNING, "Hawn | (Configuration error): Notes: " + path);
		}

		return particle;
	}

	// "255,0,255": white if it can't be read
	private static Color color(String rgb) {
		try {
			String[] parts = rgb.replace(" ", "").split(",");
			return Color.fromRGB(Integer.parseInt(parts[0]), Integer.parseInt(parts[1]), Integer.parseInt(parts[2]));
		} catch (RuntimeException e) {
			return Color.WHITE;
		}
	}
}
