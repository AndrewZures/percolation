

public class Percolation {
	
  private int[][] grid;
  private WeightedQuickUnionUF unionArray;
  private int gridWidth;
  private int sinkNum;
  
  public Percolation (int N) {
	unionArray = new WeightedQuickUnionUF(N*N+2);
    grid = new int[N][N];
    gridWidth = N;
    sinkNum = gridWidth*gridWidth+1;
    
    for(int i = 0; i < N; i++) {
      for(int j = 0; j < N; j++) {
        grid[i][j] = 0;
      }
    }
  }
  
  public void percolate(){
	  int count = 0;
	  while(!percolates()){
		  count++;
		  int randomI = (int) (Math.random() * (gridWidth));
		  int randomJ = (int) (Math.random() * (gridWidth-1))+1;
		  System.out.printf("Random: %d %d \n", randomI, randomJ); 
		  
		  //check to see if random site is open, if not, open it
		  if(!isOpen(randomI, randomJ)){
			  open(randomI, randomJ);
			  checkForConnections(randomI, randomJ);
			  printGrid();
		  } 
	  }
	  
	  if(unionArray.connected(0, sinkNum)){
		  System.out.println("Sink Num:" +sinkNum);
		  System.out.println(count);
		  System.out.println("Percolates");
	  }
  }
  
  public void checkForConnections(int i, int j){
	  if(isOpen(i,j)){
		  if(i > 0){	checkUpSite(i,j);}
		  else {unionArray.union(getOneDArrayIndex(i,j),0);}
		  			
		  if(i < gridWidth-1){checkDownSite(i,j);}
		  else {unionArray.union(getOneDArrayIndex(i,j), sinkNum);}
		  
		  if(j > 1){checkLeftSite(i,j);}
		  
		  if(j < gridWidth-1){checkRightSite(i,j);}
	  }
  }
		  

	  
public void checkUpSite(int i, int j){
	if(isOpen(i-1, j)) {unionArray.union(getOneDArrayIndex(i,j), getOneDArrayIndex(i-1, j));}
}

public void checkDownSite(int i, int j){
	if(isOpen(i+1, j)) {unionArray.union(getOneDArrayIndex(i,j), getOneDArrayIndex(i+1, j));}
}

public void checkLeftSite(int i, int j){
	if(isOpen(i, j-1)) {unionArray.union(getOneDArrayIndex(i,j), getOneDArrayIndex(i, j-1));}
}

public void checkRightSite(int i ,int j){
	if(isOpen(i, j+1)) {unionArray.union(getOneDArrayIndex(i,j), getOneDArrayIndex(i, j+1));}
}
		  
	  
	
  
  public int getOneDArrayIndex(int i, int j){
    int arrayIndex = (i * gridWidth) + j + 1;
    return arrayIndex;
  }
  
  public int[] getTwoDArrayIndex(int oneDIndex){
	  int[] twoDIndex = new int[2];
	  twoDIndex[0] = (oneDIndex-1) / gridWidth;
	  twoDIndex[1] = (oneDIndex-1) % gridWidth;
	  return twoDIndex;
  }
  
  public void open(int i, int j) {
	  if(i < 0 || i > gridWidth) throw new IndexOutOfBoundsException();
	  if(j < 1 || j > gridWidth) throw new IndexOutOfBoundsException();
    grid[i][j] = 1;
  }
  
  public boolean isOpen(int i, int j) {
	 if(i < 0 || i > gridWidth) throw new IndexOutOfBoundsException();
	 if(j < 1 || j > gridWidth) throw new IndexOutOfBoundsException();
  
    return grid[i][j] == 1;

  }
  
  public boolean isFull(int i, int j) {
		 if(i < 0 || i > gridWidth) throw new IndexOutOfBoundsException();
		 if(j < 1 || j > gridWidth) throw new IndexOutOfBoundsException();
	  return unionArray.connected(getOneDArrayIndex(i,j), 0);
  }
  
  public boolean percolates() {
    if(unionArray.connected(0, sinkNum)){
    	  printPerc();
	  return true;
    }
    return false;
  }
  
  public void printPerc(){
	  for(int i = 1; i < sinkNum; i++){
		if(unionArray.connected(0,i)){
			int[] newArray = getTwoDArrayIndex(i);
			System.out.printf("%d: %d - %d :part of connection\n", i,newArray[0], newArray[1]);
			grid[newArray[0]][newArray[1]] = 2;
		}
	  }
  }
  
  public int getUnionArrayCount() {
	  return unionArray.count();
  }
  
  public void printGrid(){
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