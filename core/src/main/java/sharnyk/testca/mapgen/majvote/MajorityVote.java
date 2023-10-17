package sharnyk.testca.mapgen.majvote;

import sharnyk.testca.mapgen.domain.neigh.Moore;
import sharnyk.testca.mapgen.domain.neigh.Neighbourhood;
import sharnyk.testca.mapgen.domain.topology.Plain;
import sharnyk.testca.mapgen.domain.topology.Topology2d;

public class MajorityVote {
  private int colorsCount;
  private int neighSize;
  private Topology2d topology;
  private Neighbourhood neigh;

  public MajorityVote(int colorsCount, int neighSize, Topology2d topology, Neighbourhood neigh) {
    this.colorsCount = colorsCount;
    this.neighSize = neighSize;
    this.topology = topology;
    this.neigh = neigh;
  }

  public MajorityVote() {
    colorsCount = 2;
    neighSize = 1;
    topology = new Plain();
    neigh = new Moore();
  }

  public int[][] changeMap(int[][] map) {
    return changeMap(map, 0, map.length-1);
  }

  public int[][] changeMap(int[][] map, int from, int to) {

    if(from < 0)
      throw new RuntimeException("Lover bound should be > 0 but " + from);
    if(to >= map.length)
      throw new RuntimeException("Upper bound should be < map.length-1 but " + to);

    int resLength = to-from+1;
    int[][] result = new int[resLength][map[0].length];

    for(int i=from; i<=to; i++) {
      for(int j=0; j<map[0].length; j++) {
        result[i-from][j] = majorityVote(i, j, map);
      }
    }

    return result;
  }

  private int majorityVote(int i, int j, int[][] map) {
    int[] count = countValues(i, j, map);
    int maxIdx = -1;
    boolean tie = false;
    for(int i0=0; i0<count.length; i0++) {
      if(maxIdx == -1 || count[i0]>count[maxIdx]) {
        maxIdx=i0;
        tie=false;
      } else if(count[i0]==count[maxIdx]) {
        tie = true;
      }

    }
    return tie ? map[i][j] : maxIdx;
  }

  private int[] countValues(int i, int j, int[][] map) {
    int[] result = new int[colorsCount];
    int[][] neigh = this.neigh.neighbourhood(topology.neighborhood(i,j,map, neighSize)); // todo main neigh line

    for(int i0=0;i0<neigh.length;i0++){
      for(int j0=0;j0<neigh[0].length;j0++){
        if(neigh[i0][j0] == -1) continue;
        if(i0 == neighSize && j0 == neighSize) continue;
        result[neigh[i0][j0]]++;
      }
    }

    return result;

  }

}