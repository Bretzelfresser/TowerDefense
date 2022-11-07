package code.map;

import code.core.Game;
import code.helper.Direction;
import code.helper.Path;
import code.helper.SquareGameObject;
import code.helper.Vec2;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Stack;
import java.util.function.Consumer;

public class TileMap extends SquareGameObject implements MouseMotionListener {

    public static final int BASE_WIDTH = 20;
    private Tile[][] map;
    private int tileSize;
    private Point spawnTile;
    private Path path;

    public TileMap(int x, int y, Tile.Type[][] map, int tileSize) {
        super(x, y, 0, 0);
        this.map = new Tile[map.length][map[0].length];
        for (int col = 0; col < map.length; col++) {
            for (int row = 0; row < map[0].length; row++) {
                this.map[col][row] = new Tile(getX() + row * tileSize, getY() + col * tileSize, tileSize, tileSize, map[col][row]);
                if (map[col][row] == Tile.Type.SPAWN_TILE) {
                    if (spawnTile != null)
                        throw new IllegalArgumentException("this map contains more then 1 spawn Tile, this isnt supported currently");
                    this.spawnTile = new Point(row, col);
                }
            }
        }
        if (this.spawnTile == null) {
            throw new IllegalArgumentException("this maps doesnt contain a Spawn tile, where should the Monsters spawn?");
        }
        this.tileSize = tileSize;
        setBounds(x, y, map.length * tileSize, map[0].length * tileSize);
    }

    /**
     * this is just used by editors to create a blank map, dont use it in active games
     */
    public TileMap(int x, int y, int tileSize) {
        super(x, y, 0, 0);
        this.map = new Tile[BASE_WIDTH][BASE_WIDTH];
        for (int col = 0; col < BASE_WIDTH; col++) {
            for (int row = 0; row < BASE_WIDTH; row++) {
                this.map[col][row] = new Tile(getX() + row * tileSize, getY() + col * tileSize, tileSize, tileSize, Tile.Type.GRASS);
            }
        }
        this.tileSize = tileSize;
        setBounds(x, y, map.length * tileSize, map[0].length * tileSize);
    }

    @Override
    public void draw(Graphics g) {
        for (int col = 0; col < map.length; col++) {
            for (int row = 0; row < map[0].length; row++) {
                map[col][row].draw(g);
            }
        }
    }

    public Path getOrCalculatePath() {
        if (path != null)
            return path;
        if (!canFindPath()){
            throw new IllegalStateException("there is no Possible path in this map");
        }
        Path.Builder builder = Path.builder();
        Vec2 direction = findDirection(this.spawnTile, new Vec2(0, 0));
        Point temp = new Point(this.spawnTile.x, spawnTile.y);
        while (checkBounds(temp.y, temp.x)) {
            direction = toTheEnd(direction, temp);
            builder.addWaypoint(new Point(temp.x, temp.y), direction.copy());
        }

        this.path = builder.build();
        return this.path;
    }

    /**
     * walks from the given point in the direction until it cant
     */
    protected Vec2 toTheEnd(Vec2 direction, Point startTile) {
        Point tmp = new Point(startTile.x, startTile.y);
        while (true) {
            Vec2 tmpDirection = findDirection(tmp, direction);
            if (!tmpDirection.equals(direction))
                return tmpDirection;
            if (!checkBounds(tmp.x, tmp.y))
                return new Vec2(5, 0);
            tmp = direction.apply(tmp);
        }
    }

    protected Vec2 findDirection(Point startTile, Vec2 comeFrom) {
        List<Vec2> directions = new ArrayList<>();
        for (Direction d : Direction.values()) {
            Vec2 direction = d.getDirection();
            if (!direction.equals(comeFrom)) {
                Point p = direction.apply(startTile);
                if (checkBounds(p.x, p.y) && this.map[p.y][p.x].getType().canWalk())
                    directions.add(direction);
            }
        }
        if (directions.size() > 1)
            throw new IllegalStateException("found more then 1 Way from Point" + startTile.toString());
        if (directions.size() == 0) {
            throw new IllegalStateException("there was no was from Point" + startTile.toString());
        }
        return directions.get(0);
    }

    public boolean canFindPath() {
        Point endTile = findEndTile(this.spawnTile);
        return endTile != null;
    }

    private Point findEndTile(Point startTile) {
        Stack<Point> stack = new Stack<>();
        List<Point> finished = new ArrayList<>(this.getColumns() * getRows());
        stack.push(startTile);
        while (!stack.isEmpty()) {
            Point currentTile = stack.pop();
            if (!startTile.equals(currentTile) && (currentTile.y == getColumns() - 1 || currentTile.x == getRows() - 1)) {
                return currentTile;
            }
            Point p = new Point(currentTile.x + 1, currentTile.y);
            if (checkBounds(p.y, p.x) && this.map[p.y][p.x].getType().canWalk() && !finished.contains(p))
                stack.add(p);
            p = new Point(currentTile.x - 1, currentTile.y);
            if (checkBounds(p.y, p.x) && this.map[p.y][p.x].getType().canWalk() && !finished.contains(p))
                stack.add(p);
            p = new Point(currentTile.x, currentTile.y + 1);
            if (checkBounds(p.y, p.x) && this.map[p.y][p.x].getType().canWalk() && !finished.contains(p))
                stack.add(p);
            p = new Point(currentTile.x, currentTile.y - 1);
            if (checkBounds(p.y, p.x) && this.map[p.y][p.x].getType().canWalk() && !finished.contains(p))
                stack.add(p);

        }
        return null;
    }

    public boolean checkBounds(int col, int row) {
        return col < this.getColumns() && row < this.getRows();
    }

    public Tile getTile(int x, int y) {
        if (isInside(x, y)) {
            int chunkX = (int) (((double) x - this.getX()) / (double) this.tileSize);
            int chunkY = (int) (((double) y - this.getY()) / (double) this.tileSize);
            return this.map[chunkY][chunkX];
        }
        return null;
    }

    /**
     * @param x the x that has to be within the chunk
     * @param y the y that has to be within the chunk
     * @return the chunk coordinates that the tile will be stored int
     * note that this will return a (-1,-1) point if not inside this map
     */
    public Point getChunkCoordinates(int x, int y) {
        if (isInside(x, y)) {
            int chunkX = (int) (((double) x - this.getX()) / (double) this.tileSize);
            int chunkY = (int) (((double) y - this.getY()) / (double) this.tileSize);
            return new Point(chunkX, chunkY);
        }
        return new Point(-1, -1);
    }

    public void doInArea(Point startChunk, Point endChunk, Consumer<Tile> runner) {
        int maxX = Math.max(startChunk.x, endChunk.x);
        int maxY = Math.max(startChunk.y, endChunk.y);
        int minX = Math.min(startChunk.x, endChunk.x);
        int minY = Math.min(startChunk.y, endChunk.y);
        for (int y = minY; y <= maxY; y++) {
            for (int x = minX; x <= maxX; x++) {
                runner.accept(map[y][x]);
            }
        }
    }

    public void forAll(Consumer<Tile> runner) {
        for (int col = 0; col < map.length; col++) {
            for (int row = 0; row < map[0].length; row++) {
                runner.accept(map[col][row]);
            }
        }
    }

    public int getColumns() {
        return this.map.length;
    }

    public int getRows() {
        return this.map[0].length;
    }

    public void updatePositions() {
        for (int col = 0; col < map.length; col++) {
            for (int row = 0; row < map[0].length; row++) {
                map[col][row].setBounds(getX() + row * tileSize, getY() + col * tileSize, tileSize, tileSize);
            }
        }
        setBounds(getX(), getY(), map.length * tileSize, map[0].length * tileSize);
    }

    public void setTileSize(int tileSize) {
        this.tileSize = tileSize;
        updatePositions();
    }


    @Override
    public void mouseDragged(MouseEvent e) {

    }

    @Override
    public void mouseMoved(MouseEvent e) {
        for (int col = 0; col < map.length; col++) {
            for (int row = 0; row < map[0].length; row++) {
                Tile t = this.map[col][row];
                if (t.isInside(e.getX(), e.getY())) {
                    t.setHovered(true);
                } else
                    t.setHovered(false);
            }
        }
    }

    public Tile[][] getData() {
        return map;
    }

    public void updateTiles(Tile.Type[][] newMap) {
        this.map = new Tile[newMap.length][newMap[0].length];
        for (int col = 0; col < newMap.length; col++) {
            for (int row = 0; row < newMap[0].length; row++) {
                this.map[col][row] = new Tile(getX() + row * tileSize, getY() + col * tileSize, tileSize, tileSize, newMap[col][row]);
            }
        }
        setBounds(getX(), getY(), map.length * tileSize, map[0].length * tileSize);
    }

    public Tile getSpawnTile() {
        return this.map[this.spawnTile.y][this.spawnTile.y];
    }
}
