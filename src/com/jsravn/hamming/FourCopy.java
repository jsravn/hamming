package com.jsravn.hamming;

import java.util.concurrent.BlockingQueue;
import java.lang.Runnable;
import java.lang.Integer;
import java.lang.Thread;

/**
 * Task that copies the input into four output queues.
 * @author James S. Ravn
 */
class FourCopy implements Runnable {
    private final BlockingQueue<Integer> in, out1, out2, out3, out4;

    FourCopy(BlockingQueue<Integer> in, BlockingQueue<Integer> out1,
	     BlockingQueue<Integer> out2, BlockingQueue<Integer> out3,
	     BlockingQueue<Integer> out4) {
	this.in = in;
	this.out1 = out1;
	this.out2 = out2;
	this.out3 = out3;
	this.out4 = out4;
    }

    public void run() {
	while (!Thread.currentThread().isInterrupted()) {
	    try {
		Integer i = in.take();
		out1.put(i);
		out2.put(i);
		out3.put(i);
		out4.put(i);
	    } catch (InterruptedException e) {
		Thread.currentThread().interrupt();
		break;
	    }
	}
    }
}