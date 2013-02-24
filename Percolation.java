/**
 * @author Andrew Zures
 * Winter 2013 - Applied Algorithms - Coursera - Princeton University
 * 
 * This class models a percolation system
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




public class Percolation {

  /*   CONSTANTS    */
  private final int TOP_SITE = 0; //virtual top site index at top of grid
	
  /*   CLASS PROPERTIES   */
  private byte[][] grid;  //2d grid N*N grid
  private WeightedQuickUnionUF mainArray; //union array
  private WeightedQuickUnionUF fillArray; //union array without sink
  private int gridSize; //private variable == to N
  private int sink; //virtual sink sight at bottom of grid
  
  
  public Percolation(int N) {

	//Union Array size in N*N+2 since N*N is for all grid sites 
    //and plus two for bottom and top virtual sites
	  
	//mainArray keeps track of entire percolation, Top to Sink
	mainArray = new WeightedQuickUnionUF(N*N+2);
	
	//fillArray keeps track of percolation, without Sink site
	//allows for solution to "Backwash" Problem
	fillArray = new WeightedQuickUnionUF(N*N+2);
	
	//create grid, assign gridSize and sink index
    grid = new byte[N][N];
    gridSize = N;
    sink = gridSize*gridSize+1;

    //Each grid site is either Open(1) or Closed(0)
    //create grid, setting all sites to 0 i.e. CLOSED
    for (int i = 0; i < N; i++) {
    		for (int j = 0; j < N; j++) {
    			grid[i][j] = 0;
    		}
    }
  }

  
  public void open(int i, int j) {
	  //determine i and j are within bounds of grid
	  if (i < 1 || i > gridSize) throw new IndexOutOfBoundsException();
	  if (j < 1 || j > gridSize) throw new IndexOutOfBoundsException();
	  
	  //check if i,j site is already open
	  //if site is not open, open site, and check surrounding sites
	  //for union array connections
	  if (!isOpen(i, j)) {
		  grid[i-1][j-1] = 1;  //site is now Open(1)
		  checkForConnections(i, j);
	  }
  }
		  
  public boolean isOpen(int i, int j) {
	  //determine if i,j site is within bounds of grid
	  if (i < 1 || i > gridSize) throw new IndexOutOfBoundsException();
	  if (j < 1 || j > gridSize) throw new IndexOutOfBoundsException();
	  
	 //return whether grid is Open(1) or Closed(0)
     return (grid[i-1][j-1] == 1);
  }
  
  public boolean isFull(int i, int j) {
	  if (i < 1 || i > gridSize) throw new IndexOutOfBoundsException();
	  if (j < 1 || j > gridSize) throw new IndexOutOfBoundsException();
	 
	  //fillArray contains all site in mainArray except those that are 
	  // (1) connected to sink and (2) without the sink, not connected
	  // to the top_site, i.e. backwash sites.
	  //These sites are "full" or to be less ambiguous "filled"
	  return fillArray.connected(TOP_SITE, getOneDArrayIndex(i, j));
  }
  
  public boolean percolates() { return mainArray.connected(TOP_SITE, sink); }
  
  private int getOneDArrayIndex(int i, int j) {
	  	//translate twoD grid index into oneD unionArray index
	    int arrayIndex = ((i-1) * gridSize) + j;
	    return arrayIndex;
  }
  
  /********************************************/
  /*		Below: Site Checking Helper Functions */
  /********************************************/
  
  
  private void checkForConnections(int i, int j) {
	  //check a site's surrounding connections
	  //based upon where that site is located within the grid 
	  
	  //check Top Edge Case
	  if (i > 1) {
		  //if not on top most line of grid, Check if above site is open
		  checkUpSite(i, j);
		  }
	  else {
		  //on top most grid line, connect to Virtual Top Site
		  mainArray.union(getOneDArrayIndex(i, j), TOP_SITE);
		  fillArray.union(getOneDArrayIndex(i, j), TOP_SITE);
	  }
	  
	  //check Bottom Edge Case
	  if (i < gridSize) { 
		  //if not on bottom most line of grid, Check if below site is open
		  checkDownSite(i, j); 
	  }
	  else { 
		  //if on bottom most grid line, connect site to sink site in mainArray
		  //NOTE: connection is not made within fillArray, differentiating the arrays
		  //and allowing for solution to backwash problem
		  mainArray.union(getOneDArrayIndex(i, j), sink); 
	  }
	  
	  //check Left Edge Case
	  if (j > 1) { checkLeftSite(i, j); }
	  
	  //check Right Edge Case
	  if (j < gridSize) { checkRightSite(i, j); }
  }
		  
  private void checkUpSite(int i, int j) {
	//if site above current site is open 
	//merge sites: Both main and fill arrays
	if (isOpen(i-1, j)) {
		mainArray.union(getOneDArrayIndex(i, j), getOneDArrayIndex(i-1, j));
		fillArray.union(getOneDArrayIndex(i, j), getOneDArrayIndex(i-1, j));
	}
  }

  private void checkDownSite(int i, int j) {
	//if site above current site is open
	//merge sites: Both for main and fill arrays
	if (isOpen(i+1, j)) {
		mainArray.union(getOneDArrayIndex(i, j), getOneDArrayIndex(i+1, j));
		fillArray.union(getOneDArrayIndex(i, j), getOneDArrayIndex(i+1, j));
	}
  }

  private void checkLeftSite(int i, int j) {
	//if site to left of current site is open
	//merge sites: Both for main and fill arrays
	if (isOpen(i, j-1)) {
	  mainArray.union(getOneDArrayIndex(i, j), getOneDArrayIndex(i, j-1));
	  fillArray.union(getOneDArrayIndex(i, j), getOneDArrayIndex(i, j-1));	
	}
  }

  private void checkRightSite(int i, int j) {
	/*if site to right of current site is open
	merge sites: Both for main and fill arrays*/
	if (isOpen(i, j+1)) {
		mainArray.union(getOneDArrayIndex(i, j), getOneDArrayIndex(i, j+1));
		fillArray.union(getOneDArrayIndex(i, j), getOneDArrayIndex(i, j+1));
	}
  }
  
}


