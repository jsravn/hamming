package jsravn.hamming;

import java.util.concurrent.BlockingQueue;
import java.lang.Runnable;
import java.lang.Integer;
import java.lang.Thread;
import java.util.ArrayList;

/**
 * Task that waits for all three input queues to be non-empty, then it takes
 * the lowest value and places it on the output queue. It discards duplicate
 * values.
 * @author James S. Ravn
 */
class ThreeMerge implements Runnable {
    private final ArrayList<BlockingQueue<Integer>> in;
    private final BlockingQueue<Integer> out;
    
    ThreeMerge(BlockingQueue<Integer> in1, BlockingQueue<Integer> in2,
	       BlockingQueue<Integer> in3, BlockingQueue<Integer> out) {
	this.in = new ArrayList<BlockingQueue<Integer>>(3);
	this.in.add(in1);
	this.in.add(in2);
	this.in.add(in3);
	this.out = out;
    }

    public void run() {
	Integer[] t = new Integer[3];
	
	while (!Thread.currentThread().isInterrupted()) {
	    try {
		Integer min = Integer.MAX_VALUE;

		for (int i = 0; i < t.length; i++) {
		    if (t[i] == null)
			t[i] = in.get(i).take();
		    if (t[i].compareTo(min) < 0)
			min = t[i];
		}

		out.put(min);

		for (int i = 0; i < t.length; i++) {
		    if (t[i].equals(min))
			t[i] = null;
		}
	    } catch (InterruptedException e) {
		Thread.currentThread().interrupt();
		break;
	    }
	}
    }
}