package sharnyk.testca.mapgen.trail;

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
        int bottom = 9;

        int pointer = 0; //relative index
        for (int j : index) {
            pointer = pointer + j;
            top = Math.max(top, pointer);
            bottom = Math.min(bottom, pointer);
        }

        maxTop = top;
        minBottom = bottom;

        if(top==0 && bottom==0)
            maxHeight = 1;
        else if(top==0 || bottom==0)
            maxHeight = top-bottom+1;
        else
            maxHeight = top-bottom-1;
    }
}
