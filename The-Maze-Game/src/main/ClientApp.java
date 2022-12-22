package main;
import java.io.IOException; 

import main.mazeGame.Game;

public class ClientApp {
    public static void main(String[] args) {
        // clear screen first
        try {
            new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
        } catch (InterruptedException | IOException e) {
            e.printStackTrace();
        }
        System.out.println("***********Welcome to the Maze Runner Game***********");
        System.out.println("\n");
        new Game();
    }
}
