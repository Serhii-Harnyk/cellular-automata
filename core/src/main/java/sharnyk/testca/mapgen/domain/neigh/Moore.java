package sharnyk.testca.mapgen.domain.neigh;

// Basic, most complete neighbourhood
public class Moore implements Neighbourhood {

  @Override
  public int[][] neighbourhood(int[][] neigh) {
    return neigh;
  }
}
