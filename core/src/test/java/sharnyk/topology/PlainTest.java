package sharnyk.topology;

import static org.junit.Assert.assertArrayEquals;

import org.junit.Test;
import sharnyk.testca.mapgen.domain.topology.Plain;


public class PlainTest {

  @Test
  public void testBlink() {
    Plain plain = new Plain();
    int[][] map = {{1,0}};

    int[][] neigh = plain.neighborhood(0,0,map,1);

    int[][] checkMap = {{-1,-1,-1},
                          {-1,1,0},
                          {-1,-1,-1}};
    assertArrayEquals(checkMap, neigh);
  }


  @Test
  public void testChess1() {
    Plain plain = new Plain();
    int[][] chessMap = {{1,0}, {0,1}};

    int[][] neigh = plain.neighborhood(0,0,chessMap,1);

    int[][] checkMap = {{-1,-1,-1},
                        {-1,1,0},
                        {-1,0,1}};
    assertArrayEquals(checkMap, neigh);
  }

  @Test
  public void testChess2() {
    Plain plain = new Plain();
    int[][] chessMap = {{1,0}, {0,1}};

    int[][] neigh = plain.neighborhood(1,1,chessMap,1);

    int[][] checkMap = new int[][]{ {1, 0, -1},
        {0, 1, -1},
        {-1,-1,-1}};
    assertArrayEquals(checkMap, neigh);
  }

  @Test
  public void testTShape() {
    Plain plain = new Plain();
    int [][] map = {{1,1,1},
                    {0,1,0},
                    {0,0,0}};

    int[][] neigh = plain.neighborhood(1,0,map,1);


    int[][] checkMap = {{-1,1,1},
                        {-1,0,1},
                        {-1,0,0}};

    assertArrayEquals(checkMap, neigh);
  }

  @Test
  public void testMiddle() {
    Plain plain = new Plain();
    int [][] map = {{1,1,1},
        {0,1,0},
        {0,0,0}};

    int[][] neigh = plain.neighborhood(1,1,map,1);


    int[][] checkMap = {{1,1,1},
        {0,1,0},
        {0,0,0}};

    assertArrayEquals(checkMap, neigh);
  }

  @Test
  public void testMiddleTwo() {
    Plain plain = new Plain();
    int [][] map = {{1,1,1},
        {0,1,0},
        {0,0,0}};

    int[][] neigh = plain.neighborhood(1,1,map,2);


    int[][] checkMap = {
        {-1,-1,-1,-1,-1},
        {-1,1,1,1,-1},
        {-1,0,1,0,-1},
        {-1,0,0,0,-1},
        {-1,-1,-1,-1,-1}};

    assertArrayEquals(checkMap, neigh);
  }


}
