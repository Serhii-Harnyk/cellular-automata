package sharnyk.trail;

import org.junit.Test;
import sharnyk.testca.mapgen.trail.Trail;

public class TrailTest {

    @Test
    public void firstTest(){
        Trail trail = new Trail(10, 10, 30, 4);

        int[][] field = trail.trail();


    }
}
