package sharnyk.testca.mapgen.ships;

import sharnyk.testca.mapgen.core.SteppedField;

import java.util.*;

public class Ships implements SteppedField {
    private final SplittableRandom random = new SplittableRandom();

    private final int height;
    private final int width;
    private final int pathLength;
    private final ShipsStats shipStats;
    private final int[][] field;
    private final Set<Position> emptyCells = new HashSet<>();


    public Ships(int width, int height, int pathLength) {
        this.width = width;
        this.height = height;
        this.pathLength = pathLength;

        this.field = new int[width][height];
        this.shipStats = new ShipsStats();
        setupCells();
        setupShips();
    }

    private void setupCells() {
        emptyCells.clear();
        for(int i = 0; i<width; i++) {
            for (int j =0; j<height; j++) {
                emptyCells.add(new Position(i ,j));
            }
        }
    }


    // 0 - hidden, 1 - hit, 2 - non-hit, 3 - missed
    @Override
    public int[][] field() {

        if(emptyCells.isEmpty())
            return field;

        if(shipStats.shots == 0) {
            prePlaceShips();
            setupCells();
        }

        shoot();

        shipStats.shots++;
        return field;
    }

    private void shoot() {
        Position position = new ArrayList<>(emptyCells).get(random.nextInt(0, emptyCells.size()));
        if(field[position.x][position.y] == 2)
            field[position.x][position.y] = 1;
        else if(field[position.x][position.y] == 0)
            field[position.x][position.y] = 3;
        else throw new RuntimeException("Wrong position!");
        emptyCells.remove(position);
    }

    /**
     * Rotation: 0 - up, 1 - right
     * //, 2 - bottom, 3 - left
     */
    private void prePlaceShips() {
        for (Ship ship : shipStats.shipsToPlace.values()) {
            while (true) {
                Position position = new ArrayList<>(emptyCells).get(random.nextInt(0, emptyCells.size()));
                boolean rotate = random.nextBoolean();

                if (rotate && goDown(position, ship.size)) break;
                if (!rotate && goRight(position, ship.size)) break;
            }
        }
    }


    private boolean goDown(Position position, int size) {
        if(position.y + size > height) return false;
        for(int i = 0; i<size; i++) {
            if(field[position.x][position.y + i] != 0)
                return false;
        }
        for(int i = 0; i<size; i++) {
            field[position.x][position.y + i] = 2;

            Position tmpPos = new Position(position.x, position.y+i);
            emptyCells.remove(tmpPos);
        }
        return true;
    }

    private boolean goRight(Position position, int size) {
        if(position.x + size > width) return false;
        for(int i = 0; i<size; i++) {
            if(field[position.x + i][position.y] != 0)
                return false;
        }
        for(int i = 0; i<size; i++) {
            field[position.x + i][position.y] = 2;

            Position tmpPos = new Position(position.x + i, position.y);
            emptyCells.remove(tmpPos);
        }
        return true;
    }

    private void setupShips() {
        shipStats.shipsToPlace.put(0, new Ship(4));
        shipStats.shipsToPlace.put(1, new Ship(3));
        shipStats.shipsToPlace.put(2, new Ship(3));
        shipStats.shipsToPlace.put(3, new Ship(2));
        shipStats.shipsToPlace.put(4, new Ship(2));
        shipStats.shipsToPlace.put(5, new Ship(2));
    }


    class ShipsStats {
        public int shots = 0;
        public Map<Integer, Ship> shipsGenerated = new HashMap<>();
        public Map<Integer, Ship> shipsToPlace = new HashMap<>();
    }

    class Ship {
        int size;
        boolean isWounded;
        boolean isSank;

        public Ship(int size) {
            this.size = size;
            isWounded = false;
            isSank = false;
        }
    }

    class Position {
        public int x;
        public int y;

        public Position(int x, int y) {
            this.x = x;
            this.y = y;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Position position = (Position) o;
            return x == position.x && y == position.y;
        }

        @Override
        public int hashCode() {
            return Objects.hash(x, y);
        }
    }


}
