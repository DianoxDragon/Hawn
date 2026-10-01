package fr.dianox.hawn.utility;

import com.cryptomorin.xseries.XMaterial;
import com.cryptomorin.xseries.XPotion;
import com.cryptomorin.xseries.XSound;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.potion.PotionEffectType;

import java.util.Optional;
import java.util.logging.Level;

/**
 * Parses materials, sounds and potion types written in the configuration files,
 * whatever the server version is (legacy names are accepted too thanks to XSeries).
 * Unknown values are logged and replaced by a fallback instead of throwing.
 */
public final class XParse {

	private XParse() {}

	public static Material material(String name, String additionalMessage) {
		Optional<XMaterial> xMaterial = name == null ? Optional.empty() : XMaterial.matchXMaterial(name);
		Material material = xMaterial.map(XMaterial::parseMaterial).orElse(null);

		if (material == null) {
			warn("material", name, additionalMessage);
			return Material.BARRIER;
		}

		return material;
	}

	public static Sound sound(String name, String additionalMessage) {
		Optional<XSound> xSound = name == null ? Optional.empty() : XSound.matchXSound(name);
		Sound sound = xSound.map(XSound::parseSound).orElse(null);

		if (sound == null) {
			warn("sound", name, additionalMessage);
			return XSound.AMBIENT_CAVE.parseSound();
		}

		return sound;
	}

	public static PotionEffectType potion(String name, String additionalMessage) {
		Optional<XPotion> xPotion = name == null ? Optional.empty() : XPotion.matchXPotion(name);
		PotionEffectType type = xPotion.map(XPotion::get).orElse(null);

		if (type == null) {
			warn("potion type", name, additionalMessage);
			return XPotion.HUNGER.get();
		}

		return type;
	}

	private static void warn(String what, String name, String additionalMessage) {
		Bukkit.getLogger().log(Level.WARNING, "Hawn | (Configuration error): The " + what + " " + name + " is not recognized, it has been replaced by another " + what);
		Bukkit.getLogger().log(Level.WARNING, "Hawn | (Configuration error): Notes: " + additionalMessage);
	}
}
