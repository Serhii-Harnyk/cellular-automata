package sharnyk.testca.mapgen.domain.topology;

public class Torus implements Topology {

  @Override
  public int[][] neighborhood(int i, int j, int[][] map, int size) {
    int[][] result = new int[size*2+1][size*2+1];
    for(int i0=0; i0<result.length; i0++) {
      for (int j0 = 0; j0 < result[0].length; j0++) {
        int i1;
        int j1;
        if (i - size + i0 < 0)
          i1 = map.length + (i - size + i0);
        else if (i - size + i0 >= map.length)
          i1 = (i - size + i0) - map.length;
        else
          i1 = i - size + i0;

        if (j - size + j0 < 0)
          j1 = map[0].length + (j - size + j0);
        else if (j - size + j0 >= map[0].length)
          j1 = (j - size + j0) - map[0].length;
        else
          j1 = j - size + j0;

        result[i0][j0] = map[i1][j1];
      }

    }
    return result;
  }
}
