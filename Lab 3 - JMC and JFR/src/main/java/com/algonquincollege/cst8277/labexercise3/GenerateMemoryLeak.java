/*****************************************************************
 * File: GenerateMemoryLeak.java Course materials (23W) CST 8277
 * 
 * @author Teddy Yap
 * 
 * @date 2020 10
 * @author (original) Mike Norman
 */
package com.algonquincollege.cst8277.labexercise3;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

/**
 *
 * <b>Description</b></br>
 * </br>
 * Class that experiences an Out-of-Memory exception
 *
 */
public class GenerateMemoryLeak {

	static BlockingQueue<byte[]> queue = new LinkedBlockingQueue<>();

	public static void main(String[] args) {

		Runnable producer = () -> {
			while (true) {
				// Generates 1Mb of raw (empty) data every 10ms
				queue.offer(new byte[ 1 * 1024 * 1024]);
				try {
					TimeUnit.MILLISECONDS.sleep(10);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		};

		Runnable consumer = () -> {
			while (true) {
				try {
					// Consumers are slower:  process every 100ms
					queue.take();
					TimeUnit.MILLISECONDS.sleep(100);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		};
		// Give each thread a recognizable name, helps profiling
		//TODO Replace "Producer Thread" with your name and student number, ex. "Teddy-Yap-0123456" 
		new Thread(producer, "Ollie-Savill-041079682").start();
		new Thread(consumer, "Consumer Thread").start();
	}

}