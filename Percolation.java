

public class Percolation {
	
  private int[][] grid;
  private WeightedQuickUnionUF unionArray;
  private WeightedQuickUnionUF fullArray;
  private int gridWidth;
  private int sinkNum;

  
  public Percolation (int N) {
	unionArray = new WeightedQuickUnionUF(N*N+2);
	fullArray = new WeightedQuickUnionUF(N*N+2);
    grid = new int[N][N];
    gridWidth = N;
    sinkNum = gridWidth*gridWidth+1;

    
    for(int i = 0; i < N; i++) {
      for(int j = 0; j < N; j++) {
        grid[i][j] = 0;
      }
    }
  }

  
  private void checkForConnections(int i, int j){
		  if(i > 1){	checkUpSite(i,j);}
		  else {
			  int index = getOneDArrayIndex(i,j);
			  unionArray.union(index,0);
			  fullArray.union(index,0);
			  }
		  			
		  if(i < gridWidth){checkDownSite(i,j);}
		  //needed for non backwash implementation
		  else {unionArray.union(getOneDArrayIndex(i,j), sinkNum);}
		  
		  if(j > 1){checkLeftSite(i,j);}
		  
		  if(j < gridWidth){checkRightSite(i,j);}

  }
		  
 
private void checkUpSite(int i, int j){
	if(isOpen(i-1, j)) {
		int index = getOneDArrayIndex(i,j);
		unionArray.union(index, getOneDArrayIndex(i-1, j));
		fullArray.union(index, getOneDArrayIndex(i-1, j));
	}
}

private void checkDownSite(int i, int j){
	if(isOpen(i+1, j)) {
		int index = getOneDArrayIndex(i,j);
		unionArray.union(index, getOneDArrayIndex(i+1, j));
		fullArray.union(index, getOneDArrayIndex(i+1, j));
	}
}

private void checkLeftSite(int i, int j){
	
	if(isOpen(i, j-1)) {
		int index = getOneDArrayIndex(i,j);
		unionArray.union(index, getOneDArrayIndex(i, j-1));
		fullArray.union(index, getOneDArrayIndex(i, j-1));
		
	}
}

private void checkRightSite(int i ,int j){
	if(isOpen(i, j+1)) {
		int index = getOneDArrayIndex(i,j);
		unionArray.union(index, getOneDArrayIndex(i, j+1));
		fullArray.union(index, getOneDArrayIndex(i, j+1));
	}
}
		  
  private int getOneDArrayIndex(int i, int j){
    int arrayIndex = ((i-1) * gridWidth) + j;
    return arrayIndex;
  }
  
  private int[] getTwoDArrayIndex(int oneDIndex){
	  int[] twoDIndex = new int[2];
	  twoDIndex[0] = (oneDIndex-1) / gridWidth;
	  twoDIndex[1] = (oneDIndex-1) % gridWidth;
	  return twoDIndex;
  }
  
  public void open(int i, int j) {
	  if(i < 1 || i > gridWidth) throw new IndexOutOfBoundsException("here");
	  if(j < 1 || j > gridWidth) throw new IndexOutOfBoundsException("here j");
	  if(!isOpen(i,j)){
		  grid[i-1][j-1] = 1;
		  checkForConnections(i, j);
	  }

  }
  
  public boolean isOpen(int i, int j) {
	 if(i < 1 || i > gridWidth) throw new IndexOutOfBoundsException();
	 if(j < 1 || j > gridWidth) throw new IndexOutOfBoundsException();
  
    return grid[i-1][j-1] == 1;

  }
  
  public boolean isFull(int i, int j) {
		 if(i < 1 || i > gridWidth) throw new IndexOutOfBoundsException();
		 if(j < 1 || j > gridWidth) throw new IndexOutOfBoundsException();
	  return fullArray.connected(0, getOneDArrayIndex(i,j));
  }
  
  public boolean percolates() {
    if(unionArray.connected(0, sinkNum)){
    	 	//printGrid();
    	 	//printPerc();
    	 	//System.out.println(sinkNum);
	  return true;
    }
    return false;
  }
  
  private void checkBottomSites(){
	  for(int j = 1; j <= gridWidth; j++){
		  int bottomSite = getOneDArrayIndex(gridWidth, j);
		  if(unionArray.connected(0, bottomSite)){
			  unionArray.union(bottomSite, sinkNum);
		  }
	  }
  }
  

  
  private int getUnionArrayCount() {
	  return unionArray.count();
  }
  
  private void printPerc(){
	  for(int i = 1; i < sinkNum; i++){
		if(unionArray.connected(0,i)){
			int[] newArray = getTwoDArrayIndex(i);
			System.out.printf("%d: %d - %d :part of connection\n", i,newArray[0], newArray[1]);
			grid[newArray[0]][newArray[1]] = 2;
		}
	  }
  }
  
private void printGrid(){
    for(int i = 0; i < grid.length; i++) {
      for(int j = 0; j < grid[0].length; j++) {
        if(j == grid[i].length-1){
          System.out.println(grid[i][j]);
        }
        else System.out.print(grid[i][j]);
      }
    }
    System.out.println("Count" + unionArray.count());
  }
}