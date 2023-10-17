package sharnyk.diasqu;

import org.junit.Test;
import sharnyk.testca.mapgen.archive.diasqu.DiaSqu;

public class DiasquTest {

  @Test
  public void testFirst() {
    DiaSqu ds = new DiaSqu();

    int[][] test =  ds.diasqu();
    int i = test.length;
  }

}
