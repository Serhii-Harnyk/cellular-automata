package sharnyk.testca.mapgen.oned.rules;

public class ShiftRightRule implements Rule1d {
    @Override
    public int apply(int[] field, int position, int neighSize) {
        return position == 0 ? 0: field[position-1];
    }
}
