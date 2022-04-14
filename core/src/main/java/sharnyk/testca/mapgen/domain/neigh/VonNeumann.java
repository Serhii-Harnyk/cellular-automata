package sharnyk.testca.mapgen.domain.neigh;

public class VonNeumann implements Neighbourhood {

  @Override
  public int[][] neighbourhood(int[][] neigh) {
    for(int i=0; i< neigh.length; i++) {
      for(int j=0; j<neigh[0].length; j++) {
        if(manhattan(i,j,neigh.length)) {
          neigh[i][j] = -1;
        }
      }
    }


    return neigh;
  }

  private boolean manhattan(int i, int j, int length) {
    int center = (length-1)/2; // length is always odd
    return Math.abs(i-center) + Math.abs(j-center) > center; // center is neigh size
  }
}
