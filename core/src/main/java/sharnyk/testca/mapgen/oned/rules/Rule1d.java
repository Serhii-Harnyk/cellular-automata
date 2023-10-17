package sharnyk.testca.mapgen.oned.rules;

public interface Rule1d {

    int apply(int[] field, int position, int neighSize);

    default boolean or(int a, int b) {
        return (a+b) >0;
    }

    default int toInt(boolean a) {
        return !a ? 1 : 0;
    }

    default boolean toBool(int a) {
        return !(a==1);
    }
}
