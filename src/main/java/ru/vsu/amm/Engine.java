package ru.vsu.amm;

import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.constructor.Constructor;
import ru.vsu.amm.model.State;
import ru.vsu.amm.model.States;

import java.io.InputStream;
import java.util.InputMismatchException;
import java.util.Map;
import java.util.Scanner;

public class Engine {
    public void startGame(String gameDescriptionFile) {
        States states = readYAML(gameDescriptionFile, States.class);

        Map.Entry<String, State> entry = states.getStates().entrySet().iterator().next();
        State currentState = states.getStates().get(entry.getKey());

        while (!currentState.getIsLast()) {
            System.out.println(currentState.getDescription());

            for (int i = 0; i < currentState.getActions().size(); i++) {
                System.out.println((i + 1) + ". " + currentState.getActions().get(i).getDescription());
            }
            int selectedState = readIntInRange(1, currentState.getActions().size());

            currentState = states.getStates().get(currentState.getActions().get(selectedState - 1).getNextState());
        }

        System.out.println(currentState.getDescription());
    }

    private int readIntInRange(int min, int max) {
        Scanner scanner = new Scanner(System.in);
        int input = Integer.MAX_VALUE;
        boolean inputValidate = false;

        do {
            try {
                input = scanner.nextInt();
                if (input >= min && input <= max) {
                    inputValidate = true;
                } else {
                    System.out.println("Not in range");
                    scanner.nextLine();
                }
            } catch (InputMismatchException exception) {
                System.out.println("Not a number");
                scanner.nextLine();
            }
        } while (!inputValidate);

        return input;
    }

    private <T> T readYAML(String fileName, Class<T> typeClass) {
        Yaml yaml = new Yaml(new Constructor(typeClass));
        InputStream inputStream = this.getClass().getClassLoader().getResourceAsStream(fileName);
        return yaml.load(inputStream);
    }
}
