package com.jsravn.hamming;

import java.util.concurrent.Executors;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.lang.InterruptedException;
import java.lang.Integer;
import java.util.ArrayList;

/**
 * Calculates the hamming numbers up to N using a concurrent data flow
 * algorithm.
 * @author James S. Ravn
 */
public class Main {
    final static int N = 60;

    public static void main(String args[]) {
	ExecutorService exec = Executors.newFixedThreadPool(6);

	ArrayList<BlockingQueue<Integer>> q = 
	    new ArrayList<BlockingQueue<Integer>>(8);
	for (int i = 0; i < 8; i++)
	    q.add(new LinkedBlockingQueue<Integer>());

	exec.submit(new FourCopy(q.get(0), q.get(1), q.get(2), q.get(3),
				 q.get(4)));
 	exec.submit(new Multiplier(q.get(1), q.get(5), 2));
  	exec.submit(new Multiplier(q.get(2), q.get(6), 3));
  	exec.submit(new Multiplier(q.get(3), q.get(7), 5));
 	exec.submit(new ThreeMerge(q.get(5), q.get(6), q.get(7), q.get(0)));
	
	q.get(0).add(1);

	int count = 0;
	int out;
	while (true) {
	    try {
		out = q.get(4).take();
	    } catch (InterruptedException e) {
		break;
	    }

	    count++;
	    System.out.printf("%3d: %d\n", count, out);
	    if (count == N)
		break;
	}

	exec.shutdownNow();
    }
}