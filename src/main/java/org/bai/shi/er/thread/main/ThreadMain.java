package org.bai.shi.er.thread.main;

import org.bai.shi.er.thread.simple.SimpleThread;

public class ThreadMain {
	public static void main(String[] args) {
		Thread simple1 = new Thread(new SimpleThread());
		simple1.start();
		Thread simple2 = new Thread(new SimpleThread());
		simple2.start();
	}
}
