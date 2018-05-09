package org.primal.behaviour;

import org.primal.entity.LivingEntity;
import org.primal.tile.Tile;
import org.primal.entity.Animal;
import org.primal.map.Map;
import java.util.concurrent.atomic.AtomicReference;

import java.awt.geom.Point2D;
import java.util.ArrayList;
import org.primal.entity.Herbivore;

public class ChaseBehaviour extends Behaviour {
    protected Point2D.Float chaseDir;
    private boolean isChasing = false;
    private volatile LivingEntity chasedAnimal;

    /**
     * Creates a chasing behaviour. The behaviour can be used to make an animal chase and eat other animals.
     * @param host = the Animal associated with this behaviour
     * @param map = the current Map
     */

    public ChaseBehaviour(Animal host, Map map) {

        super(host, map);
    }

    /**
     * Method that checks whether an animal should start chasing another. It checks surrounding tiles for herbivores and if
     * found then it is selected and the weight of this behaviour is set.
     * Otherwise it sets the weight of this behaviour only according to the fullness of the host.
     * The hungrier the animal is the higher the weight of this behaviour.
     */

    public void decide() {
        if (!isChasing) {
            ArrayList<Tile> tiles = map.getTiles(host.getX(), host.getY(), 3);
            for (Tile tile : tiles) {
                for (LivingEntity entity : tile.getLivingEntities()) {
                    if (entity instanceof Herbivore) {
                        this.isChasing = true;
                        this.chasedAnimal = entity;

                        this.weight = Math.round(100 - host.getFullness());
                        this.chaseDir = new Point2D.Float(entity.getX() - host.getX(), entity.getY() - host.getY());
                        normalize(chaseDir);

                        return;
                    }
                }
            }
            this.weight = 0;
        }
        else {
            this.weight = Math.round(100 - host.getFullness());
            this.chaseDir = new Point2D.Float(chasedAnimal.getX() - host.getX(), chasedAnimal.getY() - host.getY());
            normalize(chaseDir);
        }
    }

    /**
     * Normalizes a vector
     * @param p = the vector to be normalized
     * @return a normalized vector
     */

    protected Point2D normalize(Point2D p) {
        double x = p.getX();
        double y = p.getY();
        float abs = (float) Math.sqrt(Math.pow(x, 2) + Math.pow(y, 2));
        return new Point2D.Float((float) x/abs, (float) y / abs);
    }

    /**
     * If the animal is close enough to its prey then it will eat it. Otherwise it will move towards it.
     */

    public void act() {
        if (chasedAnimal == null) {
            System.out.println("Anima e null");
            isChasing = false;
            return;
        } else if (chasedAnimal.getX() - host.getX() < 0.3 && chasedAnimal.getY() - host.getY() < 0.3) {
            host.eat(chasedAnimal);
            System.out.println("Eaten!!");
            chasedAnimal = null;
            isChasing = false;
        } else {
            host.setDirection(this.chaseDir);
            host.move();
        }
    }
}
