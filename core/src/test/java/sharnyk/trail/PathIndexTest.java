package sharnyk.trail;

import org.junit.Assert;
import org.junit.Test;
import sharnyk.testca.mapgen.trail.PathIndex;
import sharnyk.testca.mapgen.trail.Trail;

public class PathIndexTest {

  @Test
  public void testMaxPathHeight() {
    Trail trail = new Trail(10, 10, 30, 4);

    PathIndex index = new PathIndex(new int[] {0,3,0,0,-5,-3,0,4,2,0});

    Assert.assertEquals(9, index.maxHeight);

    index = new PathIndex(new int[] {0,1,1,1,0,0,0,0,0,0});
    Assert.assertEquals(4, index.maxHeight);

    index = new PathIndex(new int[] {0,1,-1,1,-1,1,-1,1,-1,0});
    Assert.assertEquals(2, index.maxHeight);

    index = new PathIndex(new int[] {0,0,0,0,0,0,0,0,0,0});
    Assert.assertEquals(1, index.maxHeight);

    index = new PathIndex(new int[] {3, 1, 3, 2, -4, 1, 0, 2, -3, 0});
    Assert.assertEquals(10, index.maxHeight);
  }

}
