public class runPercolation {
  
  public static void main(String[] args){
    Percolation p = new Percolation(10);
 
    p.percolate();
    p.printGrid();
    System.out.println(p.getUnionArrayCount());
  }
}