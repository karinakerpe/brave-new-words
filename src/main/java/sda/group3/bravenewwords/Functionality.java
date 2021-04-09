package sda.group3.bravenewwords;

import java.util.*;
import java.util.concurrent.TimeUnit;

public class Functionality {

    // creating a map where save answers to specific questions
    Map<String, List<String>> answers = new HashMap<>();
    //for colors
    Map<String, List<String>> answersCopy = new HashMap<>();
    List<Integer> colorIndexForAnswerWords = new ArrayList<>();

    /**
     * =========ALL FOR COLORS==============
     */

    //RESET - BRINGS BACK TO NORMAL
    final String ANSI_RESET = "\u001B[0m";


    final String BLACK_BOLD_BRIGHT = "\033[1;90m"; // BLACK
    public static final String RED_BOLD_BRIGHT = "\033[1;91m";   // RED
    public static final String GREEN_BOLD_BRIGHT = "\033[1;92m"; // GREEN
    final String YELLOW_BOLD_BRIGHT = "\033[1;93m";// YELLOW
    public static final String BLUE_BOLD_BRIGHT = "\033[1;94m";  // BLUE
    public static final String PURPLE_BOLD_BRIGHT = "\033[1;95m";// PURPLE
    public static final String CYAN_BOLD_BRIGHT = "\033[1;96m";  // CYAN
    public static final String WHITE_BOLD_BRIGHT = "\033[1;97m"; // WHITE

    // High Intensity backgrounds
    final String BLACK_BACKGROUND_BRIGHT = "\033[0;100m";// BLACK
    final String RED_BACKGROUND_BRIGHT = "\033[0;101m";// RED
    final String GREEN_BACKGROUND_BRIGHT = "\033[0;102m";// GREEN
    final String YELLOW_BACKGROUND_BRIGHT = "\033[0;103m";// YELLOW
    public static final String BLUE_BACKGROUND_BRIGHT = "\033[0;104m";// BLUE
    public static final String PURPLE_BACKGROUND_BRIGHT = "\033[0;105m"; // PURPLE
    public static final String CYAN_BACKGROUND_BRIGHT = "\033[0;106m";  // CYAN
    final String WHITE_BACKGROUND_BRIGHT = "\033[0;107m";   // WHITE


    final String ANSI_BLACK = "\u001B[30m";
    final String ANSI_RED = "\u001B[31m";
    final String ANSI_GREEN = "\u001B[32m";
    final String ANSI_YELLOW = "\u001B[33m";
    final String ANSI_BLUE = "\u001B[34m";
    final String ANSI_PURPLE = "\u001B[35m";
    final String ANSI_CYAN = "\u001B[36m";
    final String ANSI_WHITE = "\u001B[37m";

    final String BLACK_UNDERLINED = "\033[4;30m";  // BLACK


    //Method for colors - random; USAGE:
    // only with SOUT and after text must use RESET; color+text+reset
    public String resetColor() {
        return ANSI_RESET;
    }

    public String setColorMainText() {
        return WHITE_BACKGROUND_BRIGHT + ANSI_BLACK;
    }

    public void printingMainText(String text) {
        System.out.println(setColorMainText() + text + resetColor());
    }

    public String setColorErrorText() {
        return RED_BACKGROUND_BRIGHT + ANSI_BLACK;
    }

    public void printingErrorText(String text) {
        System.out.println(setColorErrorText() + text + resetColor());
    }


    public void setColor() {
        int randomForI = (int) (Math.random() * 16 + 1);
        switch (randomForI) {
            case 1:
            case 6:
            case 11:
                System.out.print(BLACK_BACKGROUND_BRIGHT + BLUE_BOLD_BRIGHT);
                break;
            case 4:
            case 9:
                System.out.print(BLACK_BACKGROUND_BRIGHT + ANSI_BLUE);
                break;
            case 2:
            case 7:
            case 10:
            case 14:
                System.out.print(BLACK_BACKGROUND_BRIGHT + PURPLE_BOLD_BRIGHT);
                break;
            case 5:
                System.out.print(BLACK_BACKGROUND_BRIGHT + GREEN_BOLD_BRIGHT);
                break;
            case 12:
            case 15:
            case 16:
                System.out.print(BLACK_BACKGROUND_BRIGHT + YELLOW_BOLD_BRIGHT);
                break;
            case 3:
            case 8:
            case 13:
                System.out.print(BLACK_BACKGROUND_BRIGHT + CYAN_BOLD_BRIGHT);
                break;
        }
    }

    public void setColor(int index) {

        if (index == 1) {
            System.out.print(BLACK_BACKGROUND_BRIGHT + BLUE_BOLD_BRIGHT);
        } else if (index == 2) {
            System.out.print(BLACK_BACKGROUND_BRIGHT + YELLOW_BOLD_BRIGHT);
        } else if (index == 3) {
            System.out.print(BLACK_BACKGROUND_BRIGHT + PURPLE_BOLD_BRIGHT);
        } else if (index == 4) {
            System.out.print(BLACK_BACKGROUND_BRIGHT + CYAN_BOLD_BRIGHT);
        } else if (index == 5) {
            System.out.print(BLACK_BACKGROUND_BRIGHT + GREEN_BOLD_BRIGHT);
        }
    }

    public void setColor(List<Integer> index) {
        int indexForChoosingColorShame = index.get(0);
        if (indexForChoosingColorShame == 0) {
            System.out.print(BLACK_BACKGROUND_BRIGHT + BLUE_BOLD_BRIGHT);
        } else if (indexForChoosingColorShame == 1) {
            System.out.print(BLACK_BACKGROUND_BRIGHT + YELLOW_BOLD_BRIGHT);
        } else if (indexForChoosingColorShame == 2) {
            System.out.print(BLACK_BACKGROUND_BRIGHT + PURPLE_BOLD_BRIGHT);
        } else if (indexForChoosingColorShame == 3) {
            System.out.print(BLACK_BACKGROUND_BRIGHT + CYAN_BOLD_BRIGHT);
        } else if (indexForChoosingColorShame == 4) {
            System.out.print(BLACK_BACKGROUND_BRIGHT + GREEN_BOLD_BRIGHT);
        }
        index.remove(0);
    }
    public void emptyListAndEmptyCopyString (){
        colorIndexForAnswerWords.clear();
        answersCopy.clear();}
    /**
     * ===========END FOR COLORS ======================
     */




    //asking a question and saving the answer
    public String askQuestion(String question) {
        Scanner scanner = new Scanner(System.in);
        setColor();
        System.out.println("\u27A4 " + question + ANSI_RESET);
        return scanner.nextLine();
    }


    //adding the answer to the Map
    public void addAnswer(String question, String givenAnswer) {
        List<String> answer = answers.getOrDefault(question, new ArrayList<>());
        List<String> answerCopy = answersCopy.getOrDefault(question, new ArrayList<>());

        answer.add(givenAnswer);
        answerCopy.add(givenAnswer);//for colors
        answers.put(question, answer);
        answersCopy.put(question, answerCopy);// for colors
    }

    //getting a list of answers from the Map for specific question(key)
    public List<String> getAnswerList(String key) {
        return answers.get(key);
    }

    //getting one random word from the list created in the method getAnswerList();
    public String getWordFromList(String key) {
        if (getAnswerList(key).size() == 1) {
            return getAnswerList(key).get(0);

        } else {
            Random random = new Random();
            int randomIndex = random.nextInt(getAnswerList(key).size());
            return getAnswerList(key).get(randomIndex);
        }
    }


    // ensures that in the final story words are in the correct order
    // creating variable "index" that stores the index of the question
    public int indexOfAnswer(String key) {
        int index = 0;

        switch (key) {
            case "Who? / What?":
                index = 0;
                break;
            case "With whom / with what?":
                index = 1;
                break;
            case "Did what?":
                index = 2;
                break;
            case "Where?":
                index = 3;
                break;
            case "When?":
                index = 4;
                break;
            case "Why?":
                index = 5;
                break;
        }
        return index;
    }


    public String[] creatingTheStory(String[] keys) {
        String[] storyPlayer = new String[keys.length];
        String key;
        for (int i = 0; i < keys.length; i++) {
            key = keys[i];
            String answerOneWord = getWordFromList(key);
            int indexForColor = answersCopy.get(key).indexOf(answerOneWord);
            colorIndexForAnswerWords.add(indexForColor);

            storyPlayer[indexOfAnswer(key)] = answerOneWord;
            if (getAnswerList(key).contains(answerOneWord)) {
                int indexForRemove = getAnswerList(key).indexOf(answerOneWord);

                answers.remove(key, getAnswerList(key).remove(indexForRemove));

            }
        }
        return storyPlayer;
    }


    //printing story as string
    //getting the value - String[] theStory in Main, because each player story is saved in String[]

    public void printStory(String[] story) {
        for (int i = 0; i < story.length; i++) {

            if (!story[i].isEmpty()) {
                setColor(colorIndexForAnswerWords);
                if (i == 0) {
                    System.out.print("\u275B\u275B ");
                }
                System.out.printf(story[i].toUpperCase() + " ");
                if (i == story.length - 1) {
                    System.out.print(" \u275C\u275C" + ANSI_RESET);
                }
            } else {
                setColor(colorIndexForAnswerWords);
                System.out.print(" ... " + ANSI_RESET);

            }
        }
        System.out.println("\n");
    }

    // makes pause with message needed
    public void pause(String pauseMessage) {
        try {

            System.out.print(BLACK_BACKGROUND_BRIGHT + ANSI_BLACK +
                    pauseMessage +
                    ANSI_RESET);
            for (int i = 0; i < 4; i++) {
                TimeUnit.MICROSECONDS.sleep(555555);
                System.out.print(BLACK_BACKGROUND_BRIGHT +
                        ANSI_BLACK + "  >>  " +
                        ANSI_RESET);
            }
            System.out.println("\n");

        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    //makes pause for time needed
    public void pause(int time) {
        try {

            for (int i = 0; i < 6; i++) {
                TimeUnit.MICROSECONDS.sleep(time);
                System.out.print(BLACK_BACKGROUND_BRIGHT +
                        ANSI_BLACK + "  >>  " +
                        ANSI_RESET);
            }
            System.out.println("\n");

        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void pause() {
        try {

            for (int i = 0; i < 6; i++) {
                TimeUnit.MICROSECONDS.sleep(444444);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

// // //
}

