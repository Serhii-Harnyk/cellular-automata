package sharnyk.testca.mapgen.domain;

import java.util.SplittableRandom;

public class MapInit {

  /**
   *
   * @param height
   * @param weight
   * @param colors
   * @param density - should be between 0 and 100
   * @return
   */
  public int[][] noiseMap(int height, int weight, int colors, int density) {
    return colors == 2 ? bwMap(height, weight, density) : multicolorMap(height, weight, colors);
  }

  private int[][] multicolorMap(int height, int weight, int colors) {
    int[][] map = new int[height][weight];
    SplittableRandom splittableRandom = new SplittableRandom();
    for(int i=0; i<height; i++) {
      for(int j=0; j<weight; j++) {
        map[i][j] = splittableRandom.nextInt(0,colors);
      }
    }

    return map;

  }

  private int[][] bwMap(int height, int weight, int density) {
    int[][] map = new int[height][weight];
    SplittableRandom splittableRandom = new SplittableRandom();
    for(int i=0; i<height; i++) {
      for(int j=0; j<weight; j++) {
        int seed = splittableRandom.nextInt(0,100);
        map[i][j] = seed > density ? 1 : 0;
      }
    }

    return map;
  }

  public int countSum(int[][] map) {
    int sum = 0;
    for(int i=0; i<map.length; i++) {
      for(int j=0; j<map[0].length; j++) {
        if(map[i][j] == 1)
          sum++;
      }
    }
    return sum;
  }

}