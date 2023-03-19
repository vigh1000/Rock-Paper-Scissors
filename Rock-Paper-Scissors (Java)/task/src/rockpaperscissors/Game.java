package rockpaperscissors;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.StringReader;
import java.util.*;

public class Game {
    //private List<String> winningCombinations = Arrays.asList("rockscissors", "paperrock", "scissorspaper");
    //private List<String> loosingCombinations = Arrays.asList("rockpaper", "paperscissors", "scissorsrock");
    //private List<String> gameOptions = Arrays.asList("rock", "paper", "scissors");
    private List<String> gameOptions = new ArrayList<>();
    private List<String> loosingCombinations = new ArrayList<>();
    private List<String> winningCombinations = new ArrayList<>();

    private String userName;
    private int userScore;

    public String getUserName(){return this.userName;}
    public void setUserName(String userName){this.userName = userName;}

    public int getUserScore() {return userScore;}
    public void setUserScore(int userScore) {this.userScore = userScore;}

    public void setScoreFromFile(File ratingsFile) throws FileNotFoundException {
        try (Scanner scanner = new Scanner(ratingsFile)) {
            while (scanner.hasNextLine()) {
                //List<String> linesFromFile = new ArrayList<String>();
                //linesFromFile.add(scanner.nextLine());
                String line = scanner.nextLine();
                if (!line.contains(userName)) {
                    continue;
                }
                String[] splitLine = line.split(" ");
                setUserScore(Integer.parseInt(splitLine[1]));
            }
        }
    }

    public void setCombinations(List<String> inputOptions) {
        gameOptions.addAll(inputOptions);

        for (String gameOption : gameOptions) {
            List<String> winningAgainstOptions = new ArrayList<>();
            List<String> loosingAgainstOptions = new ArrayList<>();
            List<String> tmpOptions = new ArrayList<>();

            if ((gameOptions.get(0).equals(gameOption)) || (gameOptions.get(gameOptions.size()-1).equals(gameOption))) {
                tmpOptions.addAll(gameOptions);
                tmpOptions.remove(gameOption);

            } else {
                if (gameOptions.size() == 3) {
                    tmpOptions.add(gameOptions.get(2));
                    tmpOptions.add(gameOptions.get(0));
                } else {
                    tmpOptions.addAll(gameOptions.subList(gameOptions.indexOf(gameOption) + 1, gameOptions.size()));
                    tmpOptions.addAll(gameOptions.subList(0, gameOptions.indexOf(gameOption)));
                }
            }

            if (tmpOptions.size() == 2) {
                winningAgainstOptions.add(tmpOptions.get(1));
                loosingAgainstOptions.add(tmpOptions.get(0));
            } else {
                winningAgainstOptions.addAll(tmpOptions.subList(tmpOptions.size()/2, tmpOptions.size()));
                loosingAgainstOptions.addAll(tmpOptions.subList(0,tmpOptions.size()/2));
            }

            for (int j = 0; j < winningAgainstOptions.size(); j++) {
                winningCombinations.add(gameOption + winningAgainstOptions.get(j));
                loosingCombinations.add(gameOption + loosingAgainstOptions.get(j));
            }
        }
    }

    public String chooseRandomOption() {
        Random random = new Random();
        int chosenRandomOption = random.nextInt(gameOptions.size());
        return gameOptions.get(chosenRandomOption);
    }

    public String returnGameResult(String userInput, String cpuOutput) {
        //Draw Option
        if (userInput.equals(cpuOutput)) {
            userScore += 50;
            return String.format("There is a draw %s", userInput);
        }

        if (loosingCombinations.contains(userInput+cpuOutput)) {
            return String.format("Sorry, but the computer chose %s", cpuOutput);
        }
        if (winningCombinations.contains(userInput+cpuOutput)) {
            userScore += 100;
            return String.format("Well done. The computer chose %s and failed", cpuOutput);
        }
        return "Wrong input";
    }

    public boolean isInputCorrect(String userInput) {
        if (gameOptions.contains(userInput)) {
            return true;
        }
        return false;
    }

    public String chooseWinningOption(String input) {
        if (input.equals("paper")) return "scissors";
        if (input.equals("scissors")) return "rock";
        if (input.equals("rock")) return "paper";
        return "Wrong input";
    }
}
