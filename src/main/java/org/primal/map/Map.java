package org.primal.map;

import org.primal.tile.Tile;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.concurrent.atomic.AtomicInteger;
import java.awt.geom.Point2D;

public class Map {
    private LinkedList<Chunk> megaChunks;
    private int mapSize;
    private int chunkSize;
    private Chunk[][] chunks;
    public int width;
    public AtomicInteger entityId = new AtomicInteger(0);

    public Map(int width) {
        this.width = width;
        chunks = new Chunk[width][width];
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < width; y++) {
                chunks[x][y] = (new Chunk(x, y, this));
            }
        }
        chunkSize = 16;
        mapSize = width * chunkSize;

    }

    public LinkedList<Chunk> getMegaChunks() {
        return megaChunks;
    }

    public Chunk[][] getChunks() {
        return chunks;
    }

    public void setChunks(Chunk[][] chunks) {
        this.chunks = chunks;
    }

    public Chunk getChunk(float x, float y) {
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < width; j++) {
                Point2D chunkPosition = chunks[i][j].getPosition();
                if (x == chunkPosition.getX() && y == chunkPosition.getY()) {
                    return chunks[i][j];
                }
            }
        }
        return null;
    }

    public Tile getTile(float x, float y) {
        int xInt = (int) x;
        int yInt = (int) y;
        Chunk ch = this.getChunk(xInt / chunkSize, yInt / chunkSize);
        return ch.getTile(xInt % chunkSize, yInt % chunkSize);
    }

    public Point2D checkCollision(float x, float y) {
        if(x <= 0){return new Point2D.Float(1,0);}
        else if(y <= 0){return new Point2D.Float(0,1);}
        else if(x >= mapSize){return new Point2D.Float(-1,0);}
        else if(y >= mapSize){return new Point2D.Float(0,-1);}
        else{return new Point2D.Float(0,0);}
    }

    public boolean withinBounds(float x, float y) {
        return (x >= 0 && y >= 0 && x < mapSize && y < mapSize);
    }


    public ArrayList<Tile> getTiles(float x, float y, int radius) {
        ArrayList<Tile> tiles = new ArrayList<>();
        Tile currentTile;

        for (int i = -radius; i < (radius++); i++) {
            for (int j = -radius; j < (radius++); i++) {

                if (withinBounds(x + i, y + j)) {
                    currentTile = this.getTile(x + i, y + j);
                    tiles.add(currentTile);
                }
            }
        }
        return tiles;
    }

    public int getSize() {
        return mapSize;
    }
}
