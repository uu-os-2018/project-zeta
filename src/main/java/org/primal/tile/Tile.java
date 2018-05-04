package org.primal.tile;

import org.primal.SimObject;
import org.primal.entity.LivingEntity;
import org.primal.map.Map;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentLinkedQueue;

public class Tile extends SimObject {

    private static int size = 30;
    private ConcurrentLinkedQueue<LivingEntity> livingEntities;
    private List<Shape> pixels;

    public Tile(float x, float y, Map map) {
        super(x, y, map);
        this.livingEntities = new ConcurrentLinkedQueue<>();
        this.shape = new Rectangle((int) x * size, (int) y * size, size, size);
        this.pixels = new ArrayList<>();

        System.out.println("TILE -----------------------------");
        // TODO: avoid static division below, define it somewhere else.
        int amountOfPixels = size / 6;
        for (int i = 1; i < amountOfPixels + 1; i++) {
            for (int j = 1; j < amountOfPixels + 1; j++) {
                pixels.add(new Rectangle(i, j, amountOfPixels, amountOfPixels));
                System.out.println("i: " + i + ", j: " + j);
            }
        }
    }

    public Tile(float x, float y, Map map, ConcurrentLinkedQueue<LivingEntity> livingEntities) {
        super(x, y, map);
        this.livingEntities = livingEntities;
    }

    public List<Shape> getPixels() {
        return this.pixels;
    }

    public static int getSize() {
        return size;
    }

    public void addLivingEntity(LivingEntity ent) {
        this.livingEntities.add(ent);
    }

    public void removeLivingEntity(LivingEntity ent) {
        if (this.livingEntities.contains(ent)) {
            this.livingEntities.remove(ent);
        }
    }

    public ConcurrentLinkedQueue<LivingEntity> getLivingEntities() {
        return livingEntities;
    }

    public String toString() {
        return "Tile(x: " + this.getX() + ", y: " + this.getY() + ") has " + this.livingEntities.size() + "animals" + "%n" + this.livingEntities.toString();
    }
}
