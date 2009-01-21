package jsravn.hamming;

import java.util.concurrent.BlockingQueue;
import java.lang.Runnable;
import java.lang.Integer;
import java.lang.Thread;

/**
 * Task that multiplies the input by a given factor and places the output into
 * the output queue.
 * @author James S. Ravn
 */
class Multiplier implements Runnable {
    private final BlockingQueue<Integer> in, out;
    private final int multiplier;
    
    Multiplier(BlockingQueue<Integer> in, BlockingQueue<Integer> out,
	       int multiplier) {
	this.in = in;
	this.out = out;
	this.multiplier = multiplier;
    }

    public void run() {
	while (!Thread.currentThread().isInterrupted()) {
	    try {
		Integer i = in.take();
		out.put(i * multiplier);
	    } catch (InterruptedException e) {
		Thread.currentThread().interrupt();
		break;
	    }
	}
    }
}