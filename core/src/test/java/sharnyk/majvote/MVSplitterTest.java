package sharnyk.majvote;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

import org.junit.Test;
import sharnyk.testca.mapgen.domain.MajorityVote;
import sharnyk.testca.mapgen.domain.split.MajorityVoteConcurrentSplitter;
import sharnyk.testca.mapgen.domain.split.MajorityVoteSplitter;


public class MVSplitterTest {

  @Test
  public void testBounds() {
    MajorityVote mv = new MajorityVote();
    MajorityVoteSplitter splitter = new MajorityVoteSplitter(mv, 5);
    int [][] map = {
        {1,1,1},
        {0,1,0},
        {0,0,0},
        {1,1,1},};

    map = splitter.changeMap(map);

    int[][] checkMap = {
        {1,1,1},
        {1,0,1},
        {1,0,1},
        {0,0,0},};

    assertArrayEquals(checkMap, map);
  }

  @Test
  public void testConcurrentBounds() {
    MajorityVote mv = new MajorityVote();
    MajorityVoteConcurrentSplitter splitter = new MajorityVoteConcurrentSplitter(mv, 5);
    int [][] map = {
        {1,1,1},
        {0,1,0},
        {0,0,0},
        {1,1,1},};

    map = splitter.changeMap(map);

    int[][] checkMap = {
        {1,1,1},
        {1,0,1},
        {1,0,1},
        {0,0,0},};

    assertArrayEquals(checkMap, map);
  }


}
