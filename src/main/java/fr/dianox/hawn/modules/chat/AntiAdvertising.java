package fr.dianox.hawn.modules.chat;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * The anti-advertising of the chat: IP addresses and domain names, also written "server . net", "server(dot)net"
 * or "192 . 168 . 1 . 1". The addresses of the whitelist (your own site, your Discord invitation...) are allowed.
 */
public final class AntiAdvertising {

	// A dot written another way: " . " (a space before it), "(dot)", "[dot]", "{.}"... A dot followed by a space only is
	// the end of a sentence ("Fine. de rien" is not an address)
	private static final Pattern DOT = Pattern.compile("\\s+\\.\\s*|\\s*[(\\[{]\\s*(?:dot|point|\\.)\\s*[)\\]}]\\s*");

	private static final Pattern IP = Pattern.compile("(?<![\\d.])(\\d{1,3})\\.(\\d{1,3})\\.(\\d{1,3})\\.(\\d{1,3})(?:\\s*:\\s*\\d{1,5})?(?!\\d)");

	// The endings used by the servers and the invitation links. Common words that are also endings (so, it, me, to...)
	// are left out, so that a sentence written without spaces doesn't trigger it
	private static final Set<String> ENDINGS = new HashSet<>(Arrays.asList(
			"com", "net", "org", "fr", "de", "eu", "io", "gg", "co", "uk", "us", "ru", "xyz", "tk", "ml", "ga", "cf", "gq",
			"info", "biz", "online", "site", "club", "fun", "pro", "be", "ch", "ca", "es", "nl", "pl", "tv", "cc", "ws",
			"ly", "sh", "app", "dev", "link", "shop", "store", "live", "world", "space", "host", "network", "games",
			"top", "icu", "vip", "lol", "gl", "mc", "pw", "br", "se", "dk", "fi", "pt", "cz", "ro", "hu", "at", "au",
			"nz", "jp", "kr", "cn", "su", "ua", "kz", "ovh", "best"));

	// A name, a dot, an ending, then the end of the word
	private static final Pattern DOMAIN = Pattern.compile("(?<![\\p{L}\\p{N}-])(?:[\\p{L}\\p{N}-]+\\.)+([a-z]{2,8})(?![\\p{L}\\p{N}])");

	private AntiAdvertising() {}

	/**
	 * The first address of the message that the whitelist doesn't allow, null when there is none.
	 */
	public static String find(String message, boolean ips, boolean domains, List<String> whitelist) {
		String text = DOT.matcher(message.toLowerCase(Locale.ROOT)).replaceAll(".");

		if (ips) {
			Matcher m = IP.matcher(text);
			while (m.find()) {
				boolean valid = true;
				for (int i = 1; i <= 4; i++) {
					if (Integer.parseInt(m.group(i)) > 255) valid = false;
				}
				if (valid && !allowed(text, m, whitelist)) {
					return m.group();
				}
			}
		}

		if (domains) {
			Matcher m = DOMAIN.matcher(text);
			while (m.find()) {
				if (ENDINGS.contains(m.group(1)) && !allowed(text, m, whitelist)) {
					return m.group();
				}
			}
		}

		return null;
	}

	// The address of the whitelist (with its path: discord.gg/myserver), or one of its sub-domains (play.myserver.net)
	private static boolean allowed(String text, Matcher m, List<String> whitelist) {
		String address = m.group().replace(" ", "");

		for (String entry : whitelist) {
			String allowed = entry.toLowerCase(Locale.ROOT).trim();
			if (allowed.isEmpty()) continue;

			if (text.startsWith(allowed, m.start()) || address.equals(allowed) || address.endsWith("." + allowed)) {
				return true;
			}
		}
		return false;
	}
}
