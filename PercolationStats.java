public class PercolationStats {
	private double[] results;
	private int t;

	public PercolationStats(int N, int T) {
		if (N <= 0 || T <= 0) {
			throw new IllegalArgumentException();
		}
		t = T;
		results = new double[T];

		for (int i = 0; i < T; i++) {
			Percolation p = new Percolation(N);
			testPercolation(p, N, i);
		}
	}

	private void testPercolation(Percolation p, int N, int i) {

		int count = 0;
		while (!p.percolates()) {
			int randomI = StdRandom.uniform(N) + 1;
			int randomJ = StdRandom.uniform(N) + 1;
			// System.out.printf("Random: %d %d \n", randomI, randomJ);
			if (!p.isOpen(randomI, randomJ)) {
				p.open(randomI, randomJ);
				count++;
			}
		}
		results[i] = (double) count / (N * N);
	}

	// perform T independent computational experiments on an N-by-N grid
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

		int nArg = Integer.parseInt(args[0]);
		int tArg = Integer.parseInt(args[1]);

		PercolationStats stats = new PercolationStats(nArg, tArg);
		StdOut.print(stats.mean());
		StdOut.print(stats.stddev());
		StdOut.printf("%f, %f", stats.confidenceLo(), stats.confidenceHi());
	}

}
