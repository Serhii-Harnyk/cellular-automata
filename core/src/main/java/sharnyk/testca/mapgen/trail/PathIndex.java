package sharnyk.testca.mapgen.trail;

import java.util.Arrays;

public class PathIndex {
    public int[] index;
    public int maxHeight;
    public int maxTop;
    public int minBottom;

    public PathIndex(int[] index) {
        this.index = index;
        init(index);
    }

    private void init(int[] index) {
        int top = 0;
        int bottom = 0;

        int pointer = 0; //relative index
        for (int j : index) {
            pointer = pointer + j;
            top = Math.max(top, pointer);
            bottom = Math.min(bottom, pointer);
        }

        maxTop = top;
        minBottom = bottom;
        maxHeight = top - bottom + 1;
    }

    public int getPathLength() {
        return Arrays.stream(index).map(Math::abs).sum() + index.length;
    }
}
