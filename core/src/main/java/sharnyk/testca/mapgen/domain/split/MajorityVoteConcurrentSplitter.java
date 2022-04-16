package sharnyk.testca.mapgen.domain.split;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import sharnyk.testca.mapgen.domain.MajorityVote;

public class MajorityVoteConcurrentSplitter {

  private MajorityVote mv;

  private int splits;

  public MajorityVoteConcurrentSplitter(MajorityVote mv, int splits) {
    this.mv = mv;
    this.splits = splits;
  }

  public int[][] changeMap(int[][] map) {
    int[][] result = new int[map.length][map[0].length];
    int _splits = Math.min(splits, map.length);
    int step = map.length/_splits;

    List<CompletableFuture> futures = new ArrayList<>();
    for(int i=0; i<_splits; i++) {
      int finalI = i;
      CompletableFuture cf = CompletableFuture.
          supplyAsync(() -> fetchResult(map, finalI, step)).
              thenAccept(tempResult -> {
                for(int j = 0; j<tempResult.length; j++) {
                  System.arraycopy(tempResult[j], 0, result[finalI*step+j], 0, tempResult[j].length);
                }
              });
      futures.add(cf);
    }

    futures.forEach(CompletableFuture::join);

    return result;
  }

  private int[][] fetchResult(int[][] map, int i, int step) {
    int from = i*step;
    int to = from+step;
    if(to >= map.length)
      to = map.length-1;
    return mv.changeMap(map, from, to);
  }
}
