package Compass2.lesson5;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;



public class QueueThreeThreads {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		BlockingQueue<Job> queue = new ArrayBlockingQueue<>(100);
		
		Thread rcv = new Thread(() ->  {
			for(int index = 0; index < 3; index++) {
				
					Job j;
					try {
						j = queue.take();
						j.toProcess = j.toProcess.toUpperCase();
						j.Res.put(true);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

			}
		});
		rcv.start();
		for (int index = 0; index < 3; index++) {
			Thread trn = new Thread(()->  {
				Job j = new Job("test!!!! "+Thread.currentThread().getName());
				try {
					queue.put(j);
				} catch (InterruptedException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
					return;
				}
	
				try {
					j.Res.take();
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				System.err.println("Начинаю "+Thread.currentThread().getName());

				System.err.println("Result = " + j.toProcess);

			});
			trn.start();
		}
	}

}

class Job {
	String toProcess;
	//CountDownLatch toNotify = new CountDownLatch(1);
	BlockingQueue<Boolean> Res = new ArrayBlockingQueue<>(1);
	Job(String s) {
		toProcess = s;
	}
}