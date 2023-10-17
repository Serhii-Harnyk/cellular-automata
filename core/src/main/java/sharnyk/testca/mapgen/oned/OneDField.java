package sharnyk.testca.mapgen.oned;

import sharnyk.testca.mapgen.core.SteppedField;
import sharnyk.testca.mapgen.oned.rules.Rule1d;
import sharnyk.testca.mapgen.oned.rules.Rule30;

import java.util.SplittableRandom;

public class OneDField implements SteppedField {

    private int row;
    private final int[][] field;
    private final Rule1d rule = new Rule30();
    private final SplittableRandom splittableRandom = new SplittableRandom();

    public OneDField(int height, int width) {
        field = new int[height][width];
        row = 0;
        startCondition();

    }

    private void startCondition() {
//        field[0][field[0].length/2-1] = 1;
//        field[0][field[0].length/2] = 0;
//        field[0][field[0].length/2+1] = 1;
          field[0][333] = 1;
          field[0][666] = 1;
          for(int i = 0; i < field[0].length; i++) {
                field[0][i] = splittableRandom.nextInt(0,2);
          }
    }



    @Override
    public int[][] field() {
        row++;

        if(row >= field.length)
            return field;

        for(int i=0; i<field[row].length; i++) {
            field[row][i] = rule.apply(field[row-1], i, 1);
        }

        return field;
    }
}
