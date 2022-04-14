package sharnyk.topology;

import static org.junit.Assert.assertArrayEquals;

import org.junit.Test;
import sharnyk.testca.mapgen.domain.topology.Cylinder;

public class CylinderTest {

  @Test
  public void testTop() {
    Cylinder cylinder = new Cylinder();
    int [][] map = {
        {1,1,1},
        {0,1,0},
        {0,0,0}};

    int[][] neigh = cylinder.neighborhood(0,1,map,1);

    int[][] checkMap = {
        {0,0,0},
        {1,1,1},
        {0,1,0}};

    assertArrayEquals(checkMap, neigh);
  }

  @Test
  public void testBottom() {
    Cylinder cylinder = new Cylinder();
    int [][] map = {
        {1,1,1},
        {0,1,0},
        {0,0,0}};

    int[][] neigh = cylinder.neighborhood(2,1,map,1);

    int[][] checkMap = {
        {0,1,0},
        {0,0,0},
        {1,1,1}};

    assertArrayEquals(checkMap, neigh);
  }

  @Test
  public void testCorner() {
    Cylinder cylinder = new Cylinder();
    int [][] map = {
        {1,1,1},
        {0,1,0},
        {0,0,0}};

    int[][] neigh = cylinder.neighborhood(2,2,map,1);

    int[][] checkMap = {
        {1,0,-1},
        {0,0,-1},
        {1,1,-1}};

    assertArrayEquals(checkMap, neigh);
  }

}
