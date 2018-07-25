package semaphores;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Semaphore;

public class SemaphoresExample {
	// consumerSemaphore initialized with 0 permits
	// to ensure add of producer executes first
	static Semaphore producerSemaphore = new Semaphore(1);
	static Semaphore consumerSemaphore = new Semaphore(0);
	static List<Integer> theList = new ArrayList<>();

	public static void main(String[] args) {
		System.out.println("producerSemaphore permit=1 | consumerSemaphore permit=0");

		Thread producer = new Thread(new Producer(producerSemaphore, consumerSemaphore, theList));
		Thread consumer = new Thread(new Consumer(producerSemaphore, consumerSemaphore, theList));

		producer.start();
		consumer.start();
	}
}

class Producer implements Runnable {
	Semaphore semaphoreProducer;
	Semaphore semaphoreConsumer;
	List<Integer> list;

	public Producer(Semaphore parSemaphoreProducer, Semaphore parSemaphoreConsumer, List<Integer> parList) {
		this.semaphoreProducer = parSemaphoreProducer;
		this.semaphoreConsumer = parSemaphoreConsumer;
		this.list = parList;
	}

	@Override
	public void run() {
		for (int i = 0; i < 10; i++) {
			try {
				// Before producer can produce an item, it must acquire a permit from
				// semaphoreProducer
				semaphoreProducer.acquire();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			System.out.println("Produced Value : " + i);
			// Adding the item in the list
			list.add(i);

			// After producer produces the item,it signals semaphoreConsumer
			semaphoreConsumer.release();
		}
	}
}

class Consumer implements Runnable {

	Semaphore semaphoreProducer;
	Semaphore semaphoreConsumer;
	List<Integer> list;

	public Consumer(Semaphore parSemaphoreProducer, Semaphore parSemaphoreConsumer, List<Integer> parList) {
		this.semaphoreProducer = parSemaphoreProducer;
		this.semaphoreConsumer = parSemaphoreConsumer;
		this.list = parList;
	}

	@Override
	public void run() {
		for (int i = 0; i < 10; i++) {
			try {
				// Before consumer can consume an item, it must acquire a permit from
				// semaphoreConsumer
				semaphoreConsumer.acquire();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			System.out.println("Consumed Value : " + i);
			// consumer consuming an item
			list.get(i);

			// After consumer consumes the item, it signals semaphoreProducer
			semaphoreProducer.release();
		}
	}
}