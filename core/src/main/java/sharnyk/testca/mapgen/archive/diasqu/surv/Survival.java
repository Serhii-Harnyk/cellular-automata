package sharnyk.testca.mapgen.archive.diasqu.surv;

import java.util.AbstractMap.SimpleEntry;
import java.util.HashSet;
import java.util.Set;
import java.util.SplittableRandom;

import lombok.Getter;
import lombok.Setter;
import sharnyk.testca.mapgen.domain.neigh.Moore;
import sharnyk.testca.mapgen.domain.neigh.Neighbourhood;
import sharnyk.testca.mapgen.domain.topology.Plain;
import sharnyk.testca.mapgen.domain.topology.Topology2d;

/**
 * 0-99 - ground
 * 100-199 - sheep
 * 200-299 - wolf
 */
@Getter
@Setter
public class Survival {

  private int numOfSheeps;
  private int numOfWolves;

  private int sheepHealth;
  private int wolfHealth;
  private int grassRegen;

  private int[][] survMap;
  private int[][] grassMap;
  private int[][] fullMap;

  private Topology2d topology = new Plain();
  private Neighbourhood neigh = new Moore();
  private int neighSize = 1;
  private SplittableRandom splittableRandom = new SplittableRandom();

  private static final int SHEEP_CODE = 100;
  private static final int WOLF_CODE = 200;

  public Survival(int numOfSheeps, int numOfWolves, int sheepHealth, int wolfHealth,
      int grassRegen, int height, int width) {
    this.numOfSheeps = numOfSheeps;
    this.numOfWolves = numOfWolves;
    this.sheepHealth = sheepHealth;
    this.wolfHealth = wolfHealth;
    this.grassRegen = grassRegen;
    this.survMap = new int[height][width];
    this.grassMap = new int[height][width];
    this.fullMap = new int[height][width];

    if(numOfWolves + numOfSheeps > height*width/3)
      throw new RuntimeException("Too much animals"); //one day I'll fix this

    generateAnimals(numOfSheeps, numOfWolves);
  }

  public Survival(int[][] survMap, int sheepHealth, int wolfHealth, int grassRegen) {
    this.survMap = survMap;
    this.grassMap = new int[survMap.length][survMap[0].length];
    this.fullMap = new int[survMap.length][survMap[0].length];
    this.sheepHealth = sheepHealth;
    this.wolfHealth = wolfHealth;
    this.grassRegen = grassRegen;

  }

  private void generateAnimals(int numOfSheeps, int numOfWolves) {


    generateSpecie(numOfSheeps, SHEEP_CODE + sheepHealth);
    generateSpecie(numOfWolves, WOLF_CODE + wolfHealth);
  }

  private void generateSpecie(int num, int health) {
    for(int i = 0; i< num; i++) {
      boolean isPlaced = false;
      while (!isPlaced) {
        int h = splittableRandom.nextInt(0, survMap.length);
        int w = splittableRandom.nextInt(0, survMap[0].length);
        if (survMap[h][w] <= 0) {
          survMap[h][w] = health;
          isPlaced = true;
        }
      }
    }
  }

  // makes one step and returns the map
  public int[][] step() {
    step0();
    for(int i=0; i< fullMap.length; i++) {
      for(int j=0; j<fullMap[0].length; j++) {
        if(survMap[i][j] !=0 ){
          fullMap[i][j] = survMap[i][j];
        } else {
          fullMap[i][j] = grassMap[i][j];
        }
      }
    }
    return fullMap;
  }

  private void step0() {
    move();
    checkHealth();
    restoreGrass();
  }

  private void checkHealth() {
    for(int i=0; i< survMap.length; i++) {
      for (int j = 0; j < survMap[0].length; j++) {
        if(survMap[i][j] == WOLF_CODE || survMap[i][j] == SHEEP_CODE)
          survMap[i][j] = 0; // starvation
      }
    }
  }

  private void restoreGrass() {
  }

  private void move() {
    System.out.println("Move step ");
    Set<SimpleEntry<Integer, Integer>> skipList = new HashSet<>();
    for(int i=0; i< survMap.length; i++) {
      for (int j = 0; j < survMap[0].length; j++) {
        int p = survMap[i][j];
        if(p > 0) { // there is animal here
          if(p>= WOLF_CODE && p < WOLF_CODE + 100)
            moveWolf(i,j, survMap, skipList);
          if(isSheep(p))
            moveSheep(i,j, survMap, skipList);
        }
      }
    }
  }

  private void moveSheep(int i, int j, int[][] map, Set<SimpleEntry<Integer, Integer>> skipList) {
    int[][] neigh = this.neigh.neighbourhood(topology.neighborhood(i,j,map, neighSize));

    int ns = neighSize*2+1;
    int rand = splittableRandom.nextInt(0, ns*ns);
    int i0 = rand/ns;
    int j0 = rand%ns;

    if(neigh[i0][j0] != -1 && neigh[i0][j0] > 100) {
      int i1 = i-neighSize+i0;
      int j1 = j-neighSize+j0;
      map[i1][j1] = map[i][j]; // sheep jumps
      map[i][j] = 0; // no sheep here
      skipList.add(new SimpleEntry<>(i-neighSize+i0, j-neighSize+j0));
      //map[i1][j1]--;
      System.out.println((i1 )+ " " + (j1) + " " + map[i1][j1]);
    } else {
     // map[i][j]--; //sheep stays
      System.out.println((i)+ " " + (j) + " " + map[i][j]);
    }

  }

  private boolean isSheep(int p) {
    return p>= SHEEP_CODE && p < SHEEP_CODE + 100;
  }

  private void moveWolf(int i, int j, int[][] map, Set<SimpleEntry<Integer, Integer>> skipList) {
    if(skipList.contains(new SimpleEntry<>(i, j))) // not process one wolf twice
      return;
    int[][] neigh = this.neigh.neighbourhood(topology.neighborhood(i,j,map, neighSize));

    //seek for sheep
    boolean sheepFound = false;
    for(int i0=0; i0<neigh.length; i0++) {
      for(int j0=0; j0<neigh[0].length; j0++) {
        if(isSheep(neigh[i0][j0])) {
          int i1 = i-neighSize+i0;
          int j1 = j-neighSize+j0;
          map[i1][j1] = map[i][j]; // wolf jumps
          map[i][j] = 0; // no wolf here
          skipList.add(new SimpleEntry<>(i1, j1));
          map[i1][j1] += sheepHealth; // wolf feasts
          sheepFound = true;
          break; // end turn
        }
      }
    }

    if(!sheepFound) {
      int ns = neighSize*2+1;
      int rand = splittableRandom.nextInt(0, ns*ns);
      int i0 = rand/ns;
      int j0 = rand%ns;

      if(neigh[i0][j0] != -1 && neigh[i0][j0] < 200) {
        int i1 = i-neighSize+i0;
        int j1 = j-neighSize+j0;
        map[i1][j1] = map[i][j]; // wolf jumps
        map[i][j] = 0; // no wolf here
        skipList.add(new SimpleEntry<>(i-neighSize+i0, j-neighSize+j0));
        map[i1][j1]--;
        System.out.println((i1 )+ " " + (j1) + " " + map[i1][j1]);
      } else {
        map[i][j]--; //wolf stays
        System.out.println((i)+ " " + (j) + " " + map[i][j]);
      }

    }
  }
}
