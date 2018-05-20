package pack;

import java.util.Comparator;
import java.util.concurrent.PriorityBlockingQueue;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;

import pack.Packet;


public class PacketHandlerQueue {

	PriorityBlockingQueue<Packet> queue;
	Consumer<Packet> onDequeue;
	Thread thread;
	boolean isRunning;

	public PacketHandlerQueue(int capacity, Consumer<Packet> onDequeue) {
		this.onDequeue = onDequeue;
		Comparator<Packet> queueComparator = (p1, p2) -> p1.getDate()
				.compareTo(p2.getDate());
		queue = new PriorityBlockingQueue<Packet>(capacity, queueComparator);

		thread = new Thread(() -> {
			while (isRunning) {
				try {
					Packet pack = queue.poll(1, TimeUnit.SECONDS);
					if (pack != null)
						onDequeue.accept(pack);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		});
	}

	public void start() {
		isRunning = true;
		thread.start();
	}

	public void stop() {
		isRunning = false;
		try {
			thread.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public void add(Packet pack) {
		queue.add(pack);
	}
}