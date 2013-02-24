

public class Percolation {

  private byte[][] grid;
  private WeightedQuickUnionUF unionArray;
  private WeightedQuickUnionUF fullArray;
  private int gridWidth;
  private int sinkNum;
  
  public Percolation(int N) {
	unionArray = new WeightedQuickUnionUF(N*N+2);
	fullArray = new WeightedQuickUnionUF(N*N+2);
    grid = new byte[N][N];
    gridWidth = N;
    sinkNum = gridWidth*gridWidth+1;

    for (int i = 0; i < N; i++) {
    		for (int j = 0; j < N; j++) {
    			grid[i][j] = 0;
    		}
    }
  }

  
  public void open(int i, int j) {
	  if (i < 1 || i > gridWidth) throw new IndexOutOfBoundsException();
	  if (j < 1 || j > gridWidth) throw new IndexOutOfBoundsException();
	  if (!isOpen(i, j)) {
		  grid[i-1][j-1] = 1;
		  checkForConnections(i, j);
	  }
  }
		  
  public boolean isOpen(int i, int j) {
	  if (i < 1 || i > gridWidth) throw new IndexOutOfBoundsException();
	  if (j < 1 || j > gridWidth) throw new IndexOutOfBoundsException();
  
    return (grid[i-1][j-1] == 1);

  }
  
  public boolean isFull(int i, int j) {
	  if (i < 1 || i > gridWidth) throw new IndexOutOfBoundsException();
	  if (j < 1 || j > gridWidth) throw new IndexOutOfBoundsException();
	 
	  return fullArray.connected(0, getOneDArrayIndex(i, j));
  }
  
  public boolean percolates() { return unionArray.connected(0, sinkNum); }
  
  private int getOneDArrayIndex(int i, int j) {
	    int arrayIndex = ((i-1) * gridWidth) + j;
	    return arrayIndex;
  }
  
  private void checkForConnections(int i, int j) {
	  
	  if (i > 1) {	
		  checkUpSite(i, j);
		  }
	  else {
		  unionArray.union(getOneDArrayIndex(i, j), 0);
		  fullArray.union(getOneDArrayIndex(i, j), 0);
	  }
	  			
	  if (i < gridWidth) { checkDownSite(i, j); }
	  else { unionArray.union(getOneDArrayIndex(i, j), sinkNum); }
	  
	  if (j > 1) { checkLeftSite(i, j); }
	  
	  if (j < gridWidth) { checkRightSite(i, j); }
  }
		  
  private void checkUpSite(int i, int j) {
	  
	if (isOpen(i-1, j)) {
		unionArray.union(getOneDArrayIndex(i, j), getOneDArrayIndex(i-1, j));
		fullArray.union(getOneDArrayIndex(i, j), getOneDArrayIndex(i-1, j));
	}
  }

  private void checkDownSite(int i, int j) {
	  
	if (isOpen(i+1, j)) {
		unionArray.union(getOneDArrayIndex(i, j), getOneDArrayIndex(i+1, j));
		fullArray.union(getOneDArrayIndex(i, j), getOneDArrayIndex(i+1, j));
	}
  }

  private void checkLeftSite(int i, int j) {

	if (isOpen(i, j-1)) {
	  unionArray.union(getOneDArrayIndex(i, j), getOneDArrayIndex(i, j-1));
	  fullArray.union(getOneDArrayIndex(i, j), getOneDArrayIndex(i, j-1));	
	}
  }

  private void checkRightSite(int i, int j) {
	  
	if (isOpen(i, j+1)) {
		unionArray.union(getOneDArrayIndex(i, j), getOneDArrayIndex(i, j+1));
		fullArray.union(getOneDArrayIndex(i, j), getOneDArrayIndex(i, j+1));
	}
  }
  
}


