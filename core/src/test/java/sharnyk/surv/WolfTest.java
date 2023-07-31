package sharnyk.surv;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;
import sharnyk.testca.mapgen.domain.neigh.Diagonal;
import sharnyk.testca.mapgen.surv.Survival;

public class WolfTest {



  @Before
  public void setUp() {

  }

//  @Test
  public void testWolfEatSheep() {

    int [][] map = {
        {0,120,0},
        {0,220,0},
        {0,0,0}};

    Survival surv = new Survival(map, 20, 20, 20);

    surv.step();


    int[][] checkMap = {
        {0,240,0},
        {0,0,0},
        {0,0,0}};

    assertArrayEquals(checkMap, surv.getFullMap());
  }

  @Test
  public void testWolfInCell() {

    int [][] map = {
        {202}};

    Survival surv = new Survival(map, 20, 20, 20);

    surv.step();


    int[][] checkMap = {
        {201}};

    assertArrayEquals(checkMap, surv.getFullMap());

    surv.step();

    int[][] checkMap2 = {{0}};

    assertArrayEquals(checkMap2, surv.getFullMap());


  }

  @Test
  public void wolves5() {
    int [][] map = {
        {1,1,1,210,5},
        {210,1,0,3,1},
        {0,4,210,3,1},
        {0,1,0,3,7},
        {0,210,8,210,1},
    };

    Survival surv = new Survival(map, 20, 20, 20);

    for(int i=0; i< 10; i++) {
      surv.step();
      for(int j=0; j< map.length; j++) {
        for(int k=0; k<map.length; k++) {
          if(map[j][k]>200) {
            assertEquals(map[j][k], 210-i-1);
          }
        }
      }
    }
  }


}
