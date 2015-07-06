package cc.ly.mc.core.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

public class TimeoutManager<V extends Timeout> {

	private final Map<Integer, LinkedHashMap<Long, V>> entry;

	private final List<ReentrantLock> locks;

	private int slotNo;

	private final ReentrantLock slotLock;

	private final ScheduledExecutorService slotExecutor;

	private final ScheduledExecutorService checkExecutor;

	private final ExecutorService timeoutExecutor;

	private long timeout;

	private long period;

	private int size;

	public TimeoutManager(long timeout, long period) {
		this.timeout = timeout;
		this.period = period;
		this.size = (int) (timeout / period);
		this.slotLock = new ReentrantLock();
		this.entry = new HashMap<>();
		this.locks = new ArrayList<>();
		for (int i = 0; i < size; i++) {
			locks.add(new ReentrantLock());
			entry.put(i, new LinkedHashMap<Long, V>());
		}
		this.timeoutExecutor = createTimeoutExecutorService();
		this.slotExecutor = createSlotScheduledExecutorService();
		this.checkExecutor = createCheckScheduledExecutorService();
		startScheduler();
	}

	private void startScheduler() {
		slotExecutor.scheduleAtFixedRate(new Runnable() {

			@Override
			public void run() {
				update();
			}
		}, period / 2, period / 2, TimeUnit.MILLISECONDS);
		checkExecutor.scheduleAtFixedRate(new Runnable() {

			@Override
			public void run() {
				check();
			}
		}, period / 4, period, TimeUnit.MILLISECONDS);
	}

	private ScheduledExecutorService createTimeoutExecutorService() {
		return Executors.newSingleThreadScheduledExecutor(new ThreadFactory() {

			@Override
			public Thread newThread(Runnable r) {
				Thread t = new Thread(r, "timeout-executor");
				t.setDaemon(true);
				return t;
			}
		});
	}

	private ScheduledExecutorService createCheckScheduledExecutorService() {
		return Executors.newSingleThreadScheduledExecutor(new ThreadFactory() {

			@Override
			public Thread newThread(Runnable runnable) {
				Thread t = new Thread(runnable, "check-executor");
				t.setDaemon(true);
				return t;
			}
		});
	}

	private ScheduledExecutorService createSlotScheduledExecutorService() {
		return Executors.newSingleThreadScheduledExecutor(new ThreadFactory() {

			@Override
			public Thread newThread(Runnable runnable) {
				Thread t = new Thread(runnable, "slot-executor");
				t.setDaemon(true);
				return t;
			}
		});
	}

	private void update() {
		slotLock.lock();
		try {
			slotNo = (++slotNo) % (size * 2);
		} finally {
			slotLock.unlock();
		}
	}

	private int getNextSlotNo() {
		slotLock.lock();
		try {
			return (slotNo / 2 + slotNo % 2) % size;
		} finally {
			slotLock.unlock();
		}
	}

	private int getOldestSlotNo(int slotNo) {
		return (slotNo + 1) % size;
	}

	private void check() {
		int slot = getNextSlotNo();
		int oldestSlotNo = getOldestSlotNo(slot);
		final List<V> timeouts = new LinkedList<V>();
		lock(oldestSlotNo);
		try {
			Map<Long, V> map = entry.get(oldestSlotNo);
			Set<Entry<Long, V>> set = map.entrySet();
			for (Entry<Long, V> obj : set) {
				timeouts.add(obj.getValue());
			}
			map.clear();
		} finally {
			unlock(oldestSlotNo);
		}
		timeoutExecutor.execute(new Runnable() {

			@Override
			public void run() {
				for (V timeout : timeouts) {
					timeout.onTimeout();
				}
			}
		});
	}

	public Slot register(V v) {
		Slot slot = getSlot();
		lock(slot.getSlotNo());
		try {
			Map<Long, V> map = entry.get(slot.getSlotNo());
			map.put(slot.getKey(), v);
		} finally {
			unlock(slot.getSlotNo());
		}
		return slot;
	}

	public V deregister(Slot slot) {
		if (slot != null) {
			lock(slot.getSlotNo());
			try {
				Map<Long, V> map = entry.get(slot.getSlotNo());
				return map.remove(slot.getKey());
			} finally {
				unlock(slot.getSlotNo());
			}
		}
		return null;
	}

	public Slot update(Slot slot) {
		V v = deregister(slot);
		if (v == null) {
			return null;
		} else {
			return register(v);
		}
	}

	private void lock(int slotNo) {
		locks.get(slotNo).lock();
	}

	private void unlock(int slotNo) {
		locks.get(slotNo).unlock();
	}

	private Slot getSlot() {
		return new SlotImpl(getNextSlotNo(), System.nanoTime());
	}

	public long getTimeout() {
		return timeout;
	}

	public long getPeriod() {
		return period;
	}

	public interface Slot {

		int getSlotNo();

		long getKey();
	}

	public static class SlotImpl implements Slot {

		private int slotNo;

		private long key;

		public SlotImpl(int slotNo, long key) {
			this.slotNo = slotNo;
			this.key = key;
		}

		public void setSlot(int slotNo) {
			this.slotNo = slotNo;
		}

		public void setKey(long key) {
			this.key = key;
		}

		@Override
		public int getSlotNo() {
			return slotNo;
		}

		@Override
		public long getKey() {
			return key;
		}
	}
}