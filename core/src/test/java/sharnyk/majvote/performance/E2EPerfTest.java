package sharnyk.majvote.performance;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import org.junit.Test;
import sharnyk.testca.mapgen.majvote.MajorityVote;
import sharnyk.testca.mapgen.majvote.split.MajorityVoteConcurrentSplitter;
import sharnyk.testca.mapgen.majvote.split.MajorityVoteSplitter;
import sharnyk.testca.mapgen.majvote.MapInit;
import sharnyk.testca.mapgen.domain.neigh.Moore;
import sharnyk.testca.mapgen.domain.topology.Plain;

public class E2EPerfTest {

  @Test
  public void e2eMVTest() {

    MapInit mapInit = new MapInit();
    //int[][] map = mapInit.noiseMap(1000, 1000, 2, 50);

    for (int j = 0; j < 1; j++) {
      int[][] map = mapInit.noiseMap(1000, 1000, 2, 50);
      MajorityVote mv = new MajorityVote(2, 1, new Plain(), new Moore());
      long start = System.nanoTime();
      for (int i = 0; i < 50; i++) {
        map = mv.changeMap(map);
      }
      System.out.println("J=" + j + " Time=" +(System.nanoTime() - start) / 1000000);
    }
  }

  @Test
  public void e2eMVSplitterTest() {

    MapInit mapInit = new MapInit();

    int[][] map = mapInit.noiseMap(1000, 1000, 2, 50);
    MajorityVote mv = new MajorityVote(2, 1, new Plain(), new Moore());
    MajorityVoteSplitter splitter = new MajorityVoteSplitter(mv, 8);
    long start = System.nanoTime();
    for (int i = 0; i < 50; i++) {
      map = splitter.changeMap(map);
    }

    System.out.println(" Time=" +(System.nanoTime() - start) / 1000000);

  }

  @Test
  public void e2eMVConcurrentSplitterTest() {

    MapInit mapInit = new MapInit();

    int[][] map = mapInit.noiseMap(1000, 1000, 2, 50);
    MajorityVote mv = new MajorityVote(2, 1, new Plain(), new Moore());
    for (int j = 1; j < 20; j++) {
      MajorityVoteConcurrentSplitter splitter = new MajorityVoteConcurrentSplitter(mv, j);
      long start = System.nanoTime();
      for (int i = 0; i < 50; i++) {
        map = splitter.changeMap(map);
      }
      System.out.println("J= "+ j + " Time=" +(System.nanoTime() - start) / 1000000);

      List<Integer> l = IntStream.rangeClosed(1, 1000000).boxed().collect(Collectors.toList());
    }
  }



}
