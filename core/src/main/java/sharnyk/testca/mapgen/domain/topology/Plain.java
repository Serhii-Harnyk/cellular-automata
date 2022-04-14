package sharnyk.testca.mapgen.domain.topology;

public class Plain implements Topology {

  @Override
  public int[][] neighborhood(int i, int j, int[][] map, int size) {
    int[][] result = new int[size*2+1][size*2+1];
    for(int i0=0; i0<result.length; i0++) {
      for(int j0=0; j0<result[0].length; j0++) {
        if(i-size+i0 < 0 || j-size+j0 < 0)
          result[i0][j0] = -1;
        else if(i-size+i0 >= map.length || j-size+j0 >= map[0].length)
          result[i0][j0] = -1;
        else result[i0][j0] = map[i-size+i0][j-size+j0];
      }
    }


    return result;
  }
}
