package sharnyk.testca.mapgen.oned.rules;

public class Rule30 implements Rule1d {

    @Override
    public int apply(int[] field, int position, int neighSize) {
        int left_cell = (position == 0) ? field[field.length-1] : field[position-1];
        int center_cell = field[position];
        int right_cell = (position == field.length-1) ? field[0] : field[position+1];
        return left_cell + (1 - 2 * left_cell) * (center_cell + right_cell - center_cell*right_cell);
    }
}
