package BlockingQueue;

import java.util.Random;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

/*  The below program runs infinitely as no break condition specified. Could be changed as per requirement*/
public class BlockingQueueExample {

	public static void main(String[] args) {
		BlockingQueue<Integer> queue = new LinkedBlockingQueue<>(10);

		Thread producer = new Thread(new Producer(queue), "PRODUCER");
		Thread consumer = new Thread(new Consumer(queue), "CONSUMER");

		producer.start();
		consumer.start();
	}
}

class Producer implements Runnable {
	BlockingQueue<Integer> queue;

	// Constructor initialization
	public Producer(BlockingQueue<Integer> parQueue) {
		this.queue = parQueue;
	}

	@Override
	public void run() {
		Random random = new Random();
		int MAX_LIMIT = 5;
		while (true) {
			int element = random.nextInt(MAX_LIMIT);
			System.out.println("PRODUCED Value  " + element);
			try {
				queue.put(element);
				Thread.sleep(1000);  //Adding for better visualization of the result
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

}

class Consumer implements Runnable {
	BlockingQueue<Integer> queue;

	// Constructor initialization
	public Consumer(BlockingQueue<Integer> parQueue) {
		this.queue = parQueue;
	}

	@Override
	public void run() {
		while (true) {
			try {
				System.out.println("CONSUMED Value  " + queue.take());
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}
