package sharnyk.testca.mapgen.domain.neigh;

/**
 * Non-academic neigh just for fun
 *
 * 10101 01010 00100 01010 10101
 */
public class Diagonal implements Neighbourhood {

  @Override
  public int[][] neighbourhood(int[][] neigh) {
    boolean flag = false;
    for (int i = 0; i < neigh.length; i++) {
      for (int j = 0; j < neigh[0].length; j++) {
        if (flag) {
          flag = false;
          neigh[i][j] = -1;
        } else {
          flag = true;
        }
      }
    }

    return neigh;
  }

}
