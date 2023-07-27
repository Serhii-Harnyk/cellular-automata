package sharnyk.testca.mapgen.trail;

import java.util.List;
import java.util.SplittableRandom;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

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

    public int[][] field() {
        if(pathLength < width)
            throw new RuntimeException("Path length should be more than width of the area");

        if(height/2 < maxChunkLength)
            throw new RuntimeException("MaxChunkLength shouldn't be more than half of height");


        int[][] result = new int[width][height];

        PathIndex index = generateIndex();
        int pointerY;
        if(index.maxTop == height+index.minBottom)
            pointerY = index.maxTop;
        else
            pointerY = splittableRandom.nextInt(index.maxTop, height+index.minBottom);

        for(int i =0; i< index.index.length; i++) {
            int j = 0;
            int arrow = index.index[i];
            if(arrow >= 0) {
                while (j <= arrow) {
                    result[i][pointerY - j] = 1;
                    j++;
                }
            } else {
                while (j >= arrow) {
                    result[i][pointerY - j] = 1;
                    j--;
                }
            }
            pointerY = pointerY - arrow;
        }


        return result;
    }

    public PathIndex generateIndex() {
        int[] index = new int[width];

        /*
            As we go only forward, up and down and we should reach the last column
            the number of steps forward equal width of the field
         */
        int lengthToDistr = pathLength-width;

        /*
            every column is a bucket in which we distribute up and down steps
            with number equal lengthToDistr
            they should be distributed randomly with two requirements:
            1. number of steps in a bucket should be <= maxChunkLength
            2. total height of path should be <= height of the field
            3. there should be 0 on last column cause the goal is to reach it
        */
        List<Integer> buckets =  IntStream.range(0, width-1).boxed().collect(Collectors.toList());

        while (lengthToDistr >0 ) {

            int bucket = buckets.get(splittableRandom.nextInt(0,buckets.size()));
            index[bucket] ++;
            if(index[bucket] == maxChunkLength)
                for(int i=0; i<buckets.size(); i++) {
                    if(buckets.get(i) == bucket) {
                        buckets.remove(i);
                        break;
                    }
                }
            lengthToDistr--;
        }

        int pointerY = 0;
        int top = 0;
        int bottom = 0;
        for(int i=0; i < index.length; i++) {

            int sign;
            int curr = index[i];

            if((top - (pointerY - curr) > height) && ((pointerY + curr) - bottom > height)) {
                throw new RuntimeException("dddddd");
            }

            if(top - (pointerY - curr) >= height-1)
                sign = 1;
            else if ((pointerY + curr) - bottom >= height-1)
                sign = -1;
            else
                sign = splittableRandom.nextBoolean() ? 1 : -1;
            index[i] = index[i] * sign;

            pointerY = pointerY + index[i];
            top = Math.max(top, pointerY);
            bottom = Math.min(bottom, pointerY);
        }


        return new PathIndex(index);
    }

}
