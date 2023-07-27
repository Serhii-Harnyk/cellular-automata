package sharnyk.trail;

import org.junit.Test;
import org.junit.Assert;
import sharnyk.testca.mapgen.trail.PathIndex;
import sharnyk.testca.mapgen.trail.Trail;

public class TrailTest {

    @Test
    public void fieldTest(){
        Trail trail = new Trail(10, 10, 30, 4);

        int[][] field = trail.field();


    }

    @Test
    public void genIndexTest() {
        int pLength = 30;
        int width = 10;
        long start = System.currentTimeMillis();
        for(int i =0; i<100000; i++) {
            Trail trail = new Trail(width, 10, pLength, 4);
            PathIndex pathIndex = trail.generateIndex();
            Assert.assertEquals(pLength, pathIndex.getPathLength());
        }
        System.out.println(System.currentTimeMillis() - start);

    }

    @Test
    public void genFieldTest() {
        int pLength = 30;
        int width = 10;
        long start = System.currentTimeMillis();
        for(int i =0; i<100000; i++) {
            Trail trail = new Trail(width, 10, pLength, 5);
            int[][] field = trail.field();
            Assert.assertEquals(pLength, pathLength(field));
        }
        System.out.println(System.currentTimeMillis() - start);

        start = System.currentTimeMillis();
        for(int i =0; i<100000; i++) {
            Trail trail = new Trail(100, 100, 300, 10);
            int[][] field = trail.field();
            Assert.assertEquals(300, pathLength(field));
        }
        System.out.println(System.currentTimeMillis() - start);

    }

    private int pathLength(int[][] field) {
        int sum = 0;
        for(int[] i : field) {
            for(int j : i) {
                sum += j;
            }
        }
        return sum;
    }
}
