package org.bai.shi.er.thread.simple;

public class SimpleThread implements Runnable {

	@Override
	public synchronized void run() {
		System.out.println("thread name is " + Thread.currentThread().getName());
	}

}
