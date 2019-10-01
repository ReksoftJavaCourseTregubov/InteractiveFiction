package ru.vsu.amm;

public class App {
    private static String gameDescriptionFile = "simple_game.yaml";

    public static void main(String[] args) {
        Engine engine = new Engine();
        engine.startGame(gameDescriptionFile);
    }
}
