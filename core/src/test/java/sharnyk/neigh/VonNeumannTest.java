package sharnyk.neigh;

import static org.junit.Assert.assertArrayEquals;

import org.junit.Test;
import sharnyk.testca.mapgen.domain.neigh.VonNeumann;

public class VonNeumannTest {
  @Test
  public void testOne() {

    VonNeumann neigh = new VonNeumann();
    int [][] map = {
        {1,1,1},
        {0,1,0},
        {0,0,0}};

    int[][] vonNeumann = neigh.neighbourhood(map);

    int[][] checkMap = {
        {-1,1,-1},
        {0,1,0},
        {-1,0,-1}};

    assertArrayEquals(checkMap, vonNeumann);
  }

  @Test
  public void testTwo() {

    VonNeumann neigh = new VonNeumann();
    int [][] map = {
        {1,1,1,3,5},
        {0,1,0,3,1},
        {0,4,0,3,1},
        {0,1,0,3,7},
        {0,1,8,3,1},
    };

    int[][] vonNeumann = neigh.neighbourhood(map);

    int[][] checkMap = {
        {-1,-1,1,-1,-1},
        {-1,1,0,3,-1},
        {0,4,0,3,1},
        {-1,1,0,3,-1},
        {-1,-1,8,-1,-1},
    };


    assertArrayEquals(checkMap, vonNeumann);
  }

}
