package sharnyk.testca.mapgen.trail;

import java.util.SplittableRandom;

public class Trail {
    private final SplittableRandom splittableRandom = new SplittableRandom();

    private final int height;
    private final int width;
    private final int pathLength;
    private final int maxChunkLength;

    public Trail(int width, int height, int pathLength, int maxChunkLength) {
        this.width = width;
        this.height = height;
        this.pathLength = pathLength;
        this.maxChunkLength = maxChunkLength;
    }

    public int[][] trail() {
        if(pathLength < width)
            throw new RuntimeException("Path length should be more than width of the area");

        int[][] result = new int[width][height];

        PathIndex index = new PathIndex(new int[] {0,3,0,0,-5,-3,0,4,2,0});
        int pointerY = splittableRandom.nextInt(index.maxTop, height+index.minBottom);

        for(int i =0; i< index.index.length; i++) {
            int j = 0;
            int arrow = index.index[i];
            if(arrow >= 0) {
                while (j <= arrow) {
                    result[i][j + pointerY] = 1;
                    j++;
                }
//                pointerY =
            } else {
                while (j > arrow) {
                    result[i][j + pointerY] = 1;
                    j--;
                }
            }
            pointerY = pointerY + index.index[i];
        }


        return result;
    }

}
