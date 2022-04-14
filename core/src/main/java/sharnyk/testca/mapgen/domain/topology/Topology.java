package sharnyk.testca.mapgen.domain.topology;

public interface Topology {

    // -1 means void
    int[][] neighborhood(int i, int j, int[][] map, int size);
}
