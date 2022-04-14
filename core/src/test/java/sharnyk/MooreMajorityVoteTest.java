package sharnyk;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

import org.junit.Test;
import sharnyk.testca.mapgen.domain.MajorityVote;
import sharnyk.testca.mapgen.domain.neigh.Moore;
import sharnyk.testca.mapgen.domain.topology.Plain;


public class MooreMajorityVoteTest {

  @Test
  public void testBlink() {
    MajorityVote mmv = new MajorityVote();
    int[][] chessMap = {{1,0}};

    chessMap = mmv.changeMap(chessMap);

    int[][] checkMap = {{0,1}};
    assertArrayEquals(checkMap, chessMap);
  }

  @Test
  public void testChess() {
    MajorityVote mmv = new MajorityVote();
    int[][] chessMap = {{1,0}, {0,1}};

    chessMap = mmv.changeMap(chessMap);

    int[][] checkMap = {{0,1}, {1,0}};
    assertArrayEquals(checkMap, chessMap);
  }

  @Test
  public void testBlack() {
    MajorityVote mmv = new MajorityVote();
    int [][] map = {{0,0,0},
                    {0,1,0},
                    {0,0,0}};

    map = mmv.changeMap(map);

    int[][] checkMap = {{0,0,0},
                        {0,0,0},
                        {0,0,0}};


    assertArrayEquals(checkMap, map);
  }

  @Test
  public void testHalf() {
    MajorityVote mmv = new MajorityVote();
    int [][] map = {{1,1,1},
                    {0,1,1},
                    {0,0,0}};

    map = mmv.changeMap(map);

    assertEquals(map[1][1], 1);
  }

  @Test
  public void testTShape() {
    MajorityVote mmv = new MajorityVote();
    int [][] map = {{1,1,1},
                    {0,1,0},
                    {0,0,0}};

    map = mmv.changeMap(map);

    int[][] checkMap = {{1,1,1},
                        {1,0,1},
                        {0,0,0}};

    assertArrayEquals(checkMap, map);
  }

  @Test
  public void testRectangle() {
    MajorityVote mmv = new MajorityVote();
    int [][] map = {{1,1,1},
                    {0,1,0}};

    map = mmv.changeMap(map);

    int[][] checkMap = {{1,1,1},
                        {1,1,1}};

    assertArrayEquals(checkMap, map);
  }

  @Test
  public void testMulticolor() {
    MajorityVote mmv = new MajorityVote(4,1, new Plain(), new Moore());
    int[][] map = {
        {1,1,1},
        {2,0,2},
        {3,3,3}};
    map = mmv.changeMap(map);
    int[][] checkMap = {
        {1,1,1},
        {2,0,2},
        {3,3,3}};
    assertArrayEquals(checkMap, map);
  }

  @Test
  public void testMulticolor2() {
    MajorityVote mmv = new MajorityVote(4,1, new Plain(), new Moore());
    int[][] map = {
        {1,1,1},
        {2,2,2},
        {3,3,3}};
    map = mmv.changeMap(map);
    int[][] checkMap = {
        {2,2,2},
        {2,2,2},
        {2,2,2}};
    assertArrayEquals(checkMap, map);
  }

}
