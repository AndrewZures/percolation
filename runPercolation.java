public class runPercolation {
  
  public static void main(String[] args){
	  int N = 6;
    Percolation p = new Percolation(N);
    for(int i = 1; i <= N; i++){
    		p.open(i,N);
    }
    /*while(!p.percolates()){
		  int randomI = (int) (Math.random() * N)+1;
		  int randomJ = (int) (Math.random() * N)+1;
		  System.out.printf("Random: %d %d \n", randomI, randomJ);
		  p.open(randomI, randomJ);
		  p.printGrid();
    }*/
		  
  		if(p.percolates()){
  			System.out.print("Percolateddd");
  		}
	  //p.printPerc();
	 // p.printGrid();
    //p.printGrid();
    //System.out.println(p.getUnionArrayCount());
  }
}