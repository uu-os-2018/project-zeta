package org.primal.behaviour;

import org.primal.entity.Animal;
import org.primal.map.Map;

import java.awt.geom.Point2D;
import java.util.concurrent.ThreadLocalRandom;

public class Behaviour {
    int weight = 0;
    Map map;
    Animal host;

    public Behaviour(Animal host, Map map) {
        this.map = map;
        this.host = host;
    }

    public void decide() {
        this.weight = 10;
    }

    public void act() {

        Point2D position = host.getPosition();
        Point2D.Float direction = host.getDirection();
        System.out.println(Math.pow(direction.getX(),2) + Math.pow(direction.getY(),2));

        float speed = host.getSpeed();
        double angleChange = Math.toRadians(ThreadLocalRandom.current().nextInt(-30, 30));
        Point2D.Float newDir = direction;
        //System.out.println(dirChange);

        float newDirX = (float)newDir.getX()*(float)Math.cos(angleChange) - (float)newDir.getY()*(float)Math.sin(angleChange);
        float newDirY = (float)newDir.getX()*(float)Math.sin(angleChange) + (float)newDir.getY()*(float)Math.cos(angleChange);

        newDir = new Point2D.Float(newDirX, newDirY);
        host.setDirection(newDir);
        host.move();
    }

    public int getWeight() {
        return weight;
    }
}
