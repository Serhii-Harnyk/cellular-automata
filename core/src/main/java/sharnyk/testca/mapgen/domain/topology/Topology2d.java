package sharnyk.testca.mapgen.domain.topology;

public interface Topology2d {

    // -1 means void
    int[][] neighborhood(int i, int j, int[][] map, int size);
}
