package rockpaperscissors;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws FileNotFoundException {
        Scanner scanner = new Scanner(System.in);
        Game game = new Game();
        boolean gameIsRunning = true;

        //get Username and Score
        System.out.print("Enter your name:");
        String inputName = scanner.nextLine();
        game.setUserName(inputName);
        System.out.println("Hello, " + game.getUserName());
        game.setScoreFromFile(new File(".\\rating.txt"));

        //getAndSetRules
        String inputCombinations = scanner.nextLine();
        if (inputCombinations.isEmpty()) {
            inputCombinations = "rock,paper,scissors";
        }
        List<String> combinationsAsList = Arrays.asList(inputCombinations.split(","));
        game.setCombinations(combinationsAsList);

        System.out.println("Okay, let's start");
        while (gameIsRunning) {
            String userInput = scanner.nextLine();

            if (userInput.equals("!exit")) {
                gameIsRunning = false;
                System.out.println("Bye!");
                return;
            }

            if (userInput.equals("!rating")) {
                System.out.println("Your rating: " + game.getUserScore());
                continue;
            }

            if (!game.isInputCorrect(userInput)) {
                System.out.println("Invalid input");
                continue;
            }

            String cpuChoice = game.chooseRandomOption();
            String result = game.returnGameResult(userInput, cpuChoice);

            System.out.println(result);
        }
        //Stage 1
        //      String output = game.chooseWinningOption(userInput);
        //System.out.println(String.format("Sorry, but the computer chose %s", output));
    }
}
