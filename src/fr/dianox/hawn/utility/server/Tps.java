package fr.dianox.hawn.utility.server;

public class Tps implements Runnable {
	
	public static int TICK_COUNT = 0;
	public static long[] TICKS = new long['ǝ'];

	public static double getTPS() {
		return getTPS(100);
	}
	
	public static double getTPS(int ticks) {
		if (TICK_COUNT < ticks) {
			return 20.0D;
		}
		int target = (TICK_COUNT - 1 - ticks) % TICKS.length;
		long elapsed = System.currentTimeMillis() - TICKS[target];
		if (elapsed < 0) {
			elapsed = 0;
		}
		return ticks / (elapsed / 1000.0D);
	}
	
	public void run() {
		TICKS[(TICK_COUNT % TICKS.length)] = System.currentTimeMillis();
	    TICK_COUNT += 1;
	}

}