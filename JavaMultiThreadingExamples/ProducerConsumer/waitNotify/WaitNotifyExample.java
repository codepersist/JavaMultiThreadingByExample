package waitNotify;

import java.util.LinkedList;
import java.util.Queue;

public class WaitNotifyExample {

	public static void main(String[] args) {
		System.out.println("Producer Consumer problem using wait-notify");
		Queue<Integer> sharedQueue = new LinkedList<>();

		Thread producer = new Thread(new Producer(sharedQueue), "PRODUCER");
		Thread consumer = new Thread(new Consumer(sharedQueue), "CONSUMER");

		producer.start();
		consumer.start();
	}
}

class Producer implements Runnable {
	private Queue<Integer> theSharedQueue;

	// Constructor initialization
	public Producer(Queue<Integer> parSharedQueue) {
		this.theSharedQueue = parSharedQueue;
	}

	@Override
	public void run() {
		int i = 0;
		while (true) {
			synchronized (theSharedQueue) {
				// while condition added to avoid spurious wake up
				while (theSharedQueue.size() >= 3) {
					try {
						System.out.println("Queue full, " + "Producer waiting for " + "consumer to take from queue");
						theSharedQueue.wait();
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}

				// Putting Values in the shared list
				System.out.println("Adding to shared queue " + Thread.currentThread().getName() + "" + " value:" + ++i);

				// Adding for better visualization of the result
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				theSharedQueue.add(i);
				theSharedQueue.notify();

				// To get out of while(true) loop (running 5 times only)
				if (i > 4)
					break;
			}
		}
	}
}

class Consumer implements Runnable {
	private Queue<Integer> theSharedQueue;

	// Constructor initialization
	public Consumer(Queue<Integer> parSharedQueue) {
		this.theSharedQueue = parSharedQueue;
	}

	@Override
	public void run() {
		while (true) {
			synchronized (theSharedQueue) {
				while (theSharedQueue.size() < 1) {
					try {
						System.out.println("SharedQueue empty," + "Consumer waiting" + " for producer to put into queue");
						theSharedQueue.wait();
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
				int value = theSharedQueue.remove();
				System.out.println("Taking from shared queue " + Thread.currentThread().getName() + "" + " value:" + value);
				// To get out of while(true) loop
				if (value == 5) {
					break;
				}
				theSharedQueue.notifyAll();
			}
		}
	}
}
