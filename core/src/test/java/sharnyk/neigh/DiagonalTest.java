package sharnyk.neigh;

import static org.junit.Assert.assertArrayEquals;

import javax.xml.crypto.Data;
import org.junit.Test;
import sharnyk.testca.mapgen.domain.neigh.Diagonal;
import sharnyk.testca.mapgen.domain.neigh.VonNeumann;

public class DiagonalTest {
  @Test
  public void testOne() {

    Diagonal neigh = new Diagonal();
    int [][] map = {
        {1,1,1},
        {0,1,0},
        {0,0,0}};

    int[][] vonNeumann = neigh.neighbourhood(map);

    int[][] checkMap = {
        {1,-1,1},
        {-1,1,-1},
        {0,-1,0}};

    assertArrayEquals(checkMap, vonNeumann);
  }

  @Test
  public void testTwo() {

    Diagonal neigh = new Diagonal();
    int [][] map = {
        {1,1,1,3,5},
        {0,1,0,3,1},
        {0,4,0,3,1},
        {0,1,0,3,7},
        {0,1,8,3,1},
    };

    int[][] vonNeumann = neigh.neighbourhood(map);

    int[][] checkMap = {
        {1,-1,1,-1,5},
        {-1,1,-1,3,-1},
        {0,-1,0,-1,1},
        {-1,1,-1,3,-1},
        {0,-1,8,-1,1},
    };


    assertArrayEquals(checkMap, vonNeumann);
  }

}
