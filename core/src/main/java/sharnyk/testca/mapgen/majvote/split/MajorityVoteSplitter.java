package sharnyk.testca.mapgen.majvote.split;

import sharnyk.testca.mapgen.majvote.MajorityVote;

public class MajorityVoteSplitter {

  private MajorityVote mv;

  private int splits;

  public MajorityVoteSplitter(MajorityVote mv, int splits) {
    this.mv = mv;
    this.splits = splits;
  }

  public int[][] changeMap(int[][] map) {
    int[][] result = new int[map.length][map[0].length];
    int _splits = Math.min(splits, map.length);
    int step = map.length/_splits;

    for(int i=0; i<_splits; i++) {
      int[][] tempResult = fetchResult(map, i, step);
      for(int j = 0; j<tempResult.length; j++) {
        System.arraycopy(tempResult[j], 0, result[i*step+j], 0, tempResult[j].length);
      }
    }

    return result;
  }

  private int[][] fetchResult(int[][] map, int i, int step) {
    int from = i*step;
    int to = from+step;
    if(to >= map.length)
      to = map.length-1;
    int[][] tempResult = mv.changeMap(map, from, to);
    return tempResult;
  }
}
