package fr.dianox.hawn.utility;

import java.io.File;

/**
 * Small replacements for the Apache Commons helpers that were provided by old servers
 * (commons-lang / commons-io are not shipped anymore by recent Spigot/Paper builds).
 */
public final class StringUtils {

	private StringUtils() {}

	public static String substringBetween(String str, String open, String close) {
		if (str == null || open == null || close == null) return null;

		int start = str.indexOf(open);
		if (start == -1) return null;

		int end = str.indexOf(close, start + open.length());
		if (end == -1) return null;

		return str.substring(start + open.length(), end);
	}

	public static String deleteWhitespace(String str) {
		if (str == null) return null;

		StringBuilder sb = new StringBuilder(str.length());
		for (char c : str.toCharArray()) {
			if (!Character.isWhitespace(c)) sb.append(c);
		}
		return sb.toString();
	}

	public static int toInt(String str, int defaultValue) {
		if (str == null) return defaultValue;

		try {
			return Integer.parseInt(str.trim());
		} catch (NumberFormatException e) {
			return defaultValue;
		}
	}

	public static long sizeOfDirectory(File directory) {
		File[] files = directory.listFiles();
		if (files == null) return 0L;

		long size = 0L;
		for (File file : files) {
			size += file.isDirectory() ? sizeOfDirectory(file) : file.length();
		}
		return size;
	}
}
