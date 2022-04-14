package sharnyk.testca.mapgen.domain.neigh;

/**
 * Takes basic(Moore) neighbourhood
 * and decorates is to specific (von Neumann for example)
 */
public interface Neighbourhood {

  int[][] neighbourhood(int[][] neigh);

}
