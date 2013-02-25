/**
 * @author Andrew Zures
 * Winter 2013 - Applied Algorithms - Coursera - Princeton University
 * 
 *  This class tests the Percolation.java percolation system
 *  and reports mean, stddev, and confidence interval information
 *  concerning the number of open sites necessary for N*N grid 
 *  percolation model to percolate.
 *  
 *  Percolation Problem:
 *  Given a composite systems comprised of randomly distributed insulating 
 *  and metallic materials: what fraction of the materials 
 *  need to be metallic so that the composite system is an electrical conductor? 
 *  
 *  Alternative Example:  Given a porous landscape with water on the surface (or oil below), 
 *  under what conditions will the water be able to drain through to the bottom 
 *  (or the oil to gush through to the surface)? 
 *  
 *  Scientists have defined an abstract process known as percolation to model such situations.
 *
 */

public class PercolationStats {
	
	  /*   CLASS PROPERTIES   */
	private double[] results;  //array of doubles storing results of T experiments
	private int numExperiments;
	
	public PercolationStats(int N, int T) {
		//determine if T and N are both positive 
		if (N <= 0 || T <= 0) { throw new IllegalArgumentException(); }
		
		numExperiments = T;
		results = new double[T];

		//for T experiments, create Percolation 
		//and run the required tests
		for (int i = 0; i < T; i++) {
			Percolation p = new Percolation(N);
			runPercolationTests(p, N, i);
		}
	}

	private void runPercolationTests(Percolation p, int N, int i) {
	// perform T independent computational experiments on an N-by-N grid
		int openSiteCounter = 0;
		while (!p.percolates()) {
			//While Percolation Object has not yet percolated
			
			//create two random i and j numbers within N*N grid
			int randomI = StdRandom.uniform(N) + 1;
			int randomJ = StdRandom.uniform(N) + 1;

			//determine if a site at random i,j coordinates is open
			//if not, open site and increment openSiteCounterer
			if (!p.isOpen(randomI, randomJ)) {
				p.open(randomI, randomJ);
				openSiteCounter++;
			}
		}
		
		//using OpenSitesCounter, calculate and return % of open sites
		//needed for N*N grid to percolation
		results[i] = (double) openSiteCounter / (N * N);
	}

	/* Below: Calculations Based Upon Results    */
	
	public double mean() {
		return StdStats.mean(results);
	}

	public double stddev() {
		return StdStats.stddev(results);
	}

	public double confidenceLo() {
		return StdStats.mean(results) - (1.96 * StdStats.stddev(results))
				/ Math.sqrt(numExperiments);
	}

	public double confidenceHi() {
		return StdStats.mean(results) + (1.96 * StdStats.stddev(results))
				/ Math.sqrt(numExperiments);
	}

	/*  Below:  Main Method   */
	
	public static void main(String[] args) {

		//take in N and T arguments
		int nArg = Integer.parseInt(args[0]);
		int tArg = Integer.parseInt(args[1]);

		//run percolation tests using N and T
		PercolationStats stats = new PercolationStats(nArg, tArg);
		
		//print results of PercolationStats
		StdOut.print(stats.mean());
		StdOut.print(stats.stddev());
		StdOut.printf("%f, %f", stats.confidenceLo(), stats.confidenceHi());
	}

}
