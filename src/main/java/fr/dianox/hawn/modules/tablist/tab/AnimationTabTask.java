package fr.dianox.hawn.modules.tablist.tab;

import java.util.List;

/**
 * One animation of the tab lists: its frames, shown one after the other (planned through Tasks since 1.5.6).
 */
public class AnimationTabTask implements Runnable {

	private final List<String> frames;
	private volatile int frame = 0;

	public AnimationTabTask(List<String> frames) {
		this.frames = frames;
	}

	@Override
	public void run() {
		if (!frames.isEmpty()) {
			frame = (frame + 1) % frames.size();
		}
	}

	public String current() {
		return frames.isEmpty() ? "" : frames.get(Math.min(frame, frames.size() - 1));
	}
}
