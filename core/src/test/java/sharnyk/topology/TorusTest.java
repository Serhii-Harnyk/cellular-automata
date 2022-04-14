package sharnyk.topology;

import static org.junit.Assert.assertArrayEquals;

import org.junit.Test;
import sharnyk.testca.mapgen.domain.topology.Torus;

public class TorusTest {

  @Test
  public void testTop() {
    Torus topology = new Torus();
    int [][] map = {
        {1,1,1},
        {0,1,0},
        {0,0,0}};

    int[][] neigh = topology.neighborhood(0,1,map,1);

    int[][] checkMap = {
        {0,0,0},
        {1,1,1},
        {0,1,0}};

    assertArrayEquals(checkMap, neigh);
  }

  @Test
  public void testBottom() {
    Torus topology = new Torus();
    int [][] map = {
        {1,1,1},
        {0,1,0},
        {0,0,0}};

    int[][] neigh = topology.neighborhood(2,1,map,1);

    int[][] checkMap = {
        {0,1,0},
        {0,0,0},
        {1,1,1}};

    assertArrayEquals(checkMap, neigh);
  }

  @Test
  public void testCorner() {
    Torus topology = new Torus();
    int [][] map = {
        {1,1,1},
        {0,1,0},
        {0,0,0}};

    int[][] neigh = topology.neighborhood(2,2,map,1);

    int[][] checkMap = {
        {1,0,0},
        {0,0,0},
        {1,1,1}};

    assertArrayEquals(checkMap, neigh);
  }

}
