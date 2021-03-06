package org.primal;

import org.primal.map.Map;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;

public class Main {

    public int windowWidth = 600;
    public int windowHeight = 600;
    //public static GUI gui;
    private Map map;
    public static Simulation simulation;

    public static void main(String[] args) {
        File dir = new File("output/");
        dir.mkdir();

        // Set custom error log
        try {
            @SuppressWarnings("resource")
            PrintStream stream = new PrintStream("output/error.log");
            System.setErr(stream);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        Map map = new Map(16);
        GUI gui = new GUI(map);

        Runnable action = () -> {
            //System.out.println("In synchAction");
            gui.repaint();
        };
        simulation = new Simulation(map, action);

        gui.setVisible(true);

        simulation.start();

    }
}
