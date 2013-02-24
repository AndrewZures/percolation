public class PercolationStats {
	private double[] results;
	private int t;

	public PercolationStats(int N, int T) {
		if (N <= 0 || T <= 0) {
			throw new IllegalArgumentException();
		}
		t = T;
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


	public double mean() {
		return StdStats.mean(results);
	}

	public double stddev() {
		return StdStats.stddev(results);
	}

	public double confidenceLo() {
		return StdStats.mean(results) - (1.96 * StdStats.stddev(results))
				/ Math.sqrt(t);
	}

	public double confidenceHi() {
		return StdStats.mean(results) + (1.96 * StdStats.stddev(results))
				/ Math.sqrt(t);
	}

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
