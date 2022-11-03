package code.map;

import code.core.Game;
import code.helper.SquareGameObject;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.util.Arrays;
import java.util.function.Consumer;

public class TileMap extends SquareGameObject implements MouseMotionListener {

    public static final int BASE_WIDTH = 20;
    private Tile[][] map;
    private int tileSize;
    private Tile spawnTile;

    public TileMap(int x, int y, Tile.Type[][] map, int tileSize) {
        super(x, y, 0, 0);
        this.map = new Tile[map.length][map[0].length];
        for (int col = 0; col < map.length; col++) {
            for (int row = 0; row < map[0].length; row++) {
                this.map[col][row] = new Tile(getX() + row * tileSize, getY() + col * tileSize, tileSize, tileSize, map[col][row]);
                if (map[col][row] == Tile.Type.SPAWN_TILE) {
                    if (spawnTile != null)
                        throw new IllegalArgumentException("this map contains more then 1 spawn Tile, this isnt supported currently");
                    this.spawnTile = this.map[col][row];
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
        return spawnTile;
    }
}
