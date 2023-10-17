package sharnyk.testca.mapgen.archive.diasqu;
import java.util.SplittableRandom;

public class DiaSqu {

  private SplittableRandom splittableRandom = new SplittableRandom();

  int top = 15;
  int rand = 2;

  int N = 5;

  public int[][] diasqu() {
    int size = (1 << N) +1; // the same as 2^N + 1
    int[][] map = new int[size][size];

    step(map, 0, 0, N);

    return map;
  }

  private void step(int[][] map, int i, int j, int N) {
    if(N == 0) return;
    int size = (1 << N);
    if(map[i][j] == 0)
      map[i][j] = splittableRandom.nextInt(1, top+1);
    if(map[i][j+size] == 0)
      map[i][j+size] = splittableRandom.nextInt(1, top+1);
    if(map[i+size][j] == 0)
      map[i+size][j] = splittableRandom.nextInt(1, top+1);
    if(map[i+size][j+size] == 0)
      map[i+size][j+size] = splittableRandom.nextInt(1, top+1);

    int mid = size/2;

    // diamond step
    map[i+mid][j+mid] = (map[i][j] + map[i][j+size] + map[i+size][j] + map[i+size][j+size])/4;

    //square step
    map[i][j+mid] = (map[i][j] + map[i+mid][j+mid] + map[i][j+size])/3;
    map[i+mid][j] = (map[i][j] + map[i+mid][j+mid] + map[i+mid][j])/3;
    map[i+size][j+mid] = (map[i+size][j] + map[i+mid][j+mid] + map[i+size][j+size])/3;
    map[i+mid][j+size] = (map[i][j+size] + map[i+mid][j+mid] + map[i+mid][j+size])/3;

    step(map, i, j, N-1);
    step(map, i+mid, j, N-1);
    step(map, i, j+mid, N-1);
    step(map, i+mid, j+mid, N-1);
  }

}
