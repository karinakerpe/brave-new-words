package sda.group3.bravenewwords;

import java.util.*;

public class Functionality {

    // creating a map where save answers to specific questions
    Map<String, List<String>> answers = new HashMap<>();


    /**
     * =========KRĀSAS=======================================================
     */

    //RESET - BRINGS BACK TO NORMAL
    final String ANSI_RESET = "\u001B[0m";
    //

    final String BLACK_BOLD_BRIGHT = "\033[1;90m"; // BLACK
    public static final String RED_BOLD_BRIGHT = "\033[1;91m";   // RED
    public static final String GREEN_BOLD_BRIGHT = "\033[1;92m"; // GREEN
    public static final String YELLOW_BOLD_BRIGHT = "\033[1;93m";// YELLOW
    public static final String BLUE_BOLD_BRIGHT = "\033[1;94m";  // BLUE
    public static final String PURPLE_BOLD_BRIGHT = "\033[1;95m";// PURPLE
    public static final String CYAN_BOLD_BRIGHT = "\033[1;96m";  // CYAN
    public static final String WHITE_BOLD_BRIGHT = "\033[1;97m"; // WHITE

    // High Intensity backgrounds
    final String BLACK_BACKGROUND_BRIGHT = "\033[0;100m";// BLACK
    final String RED_BACKGROUND_BRIGHT = "\033[0;101m";// RED
    public static final String GREEN_BACKGROUND_BRIGHT = "\033[0;102m";// GREEN
    public static final String YELLOW_BACKGROUND_BRIGHT = "\033[0;103m";// YELLOW
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


    /**
     * ===========KRĀSAS BEIGAS=============================
     */
    //Method for colors - random; USAGE: only with SOUT and after text must use RESET; color+text+reset
   public void setColorMainText (){
       System.out.print(WHITE_BACKGROUND_BRIGHT+ANSI_BLACK);
   }
    public void setColorErrorText (){
        System.out.print(RED_BACKGROUND_BRIGHT+ANSI_BLACK);
    }

    public void setColor() {
        int randomForI = (int) (Math.random() * 18 + 1);
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
                System.out.print(BLACK_BACKGROUND_BRIGHT + PURPLE_BOLD_BRIGHT);
                break;
            case 5:
                System.out.print(BLACK_BACKGROUND_BRIGHT + GREEN_BOLD_BRIGHT);
                break;
            case 12:
                System.out.print(YELLOW_BACKGROUND_BRIGHT + ANSI_BLACK);
                break;
            case 10:
            case 14:
                System.out.print(YELLOW_BACKGROUND_BRIGHT + PURPLE_BOLD_BRIGHT);
                break;
            case 15:
            case 16:
                System.out.print(BLACK_BACKGROUND_BRIGHT + YELLOW_BOLD_BRIGHT);
                break;
            case 3:
            case 8:
            case 13:
                System.out.print(BLACK_BACKGROUND_BRIGHT + CYAN_BOLD_BRIGHT);
                break;
            case 17:
            case 18:
                System.out.print(BLACK_BACKGROUND_BRIGHT + ANSI_BLACK);
                break;
        }
    }


    //asking a question and saving the answer
    public String askQuestion(String question) {
        Scanner scanner = new Scanner(System.in);
        setColor();
        System.out.println(question + ANSI_RESET);
        return scanner.nextLine();
    }


    //adding the answer to the Map
    public void addAnswer(String question, String givenAnswer) {
        List<String> answer = answers.getOrDefault(question, new ArrayList<>());
        answer.add(givenAnswer);
        answers.put(question, answer);
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
            case "With whom / what?":
                index = 1;
                break;
            case "When?":
                index = 2;
                break;
            case "Where?":
                index = 3;
                break;
            case "Did what?":
                index = 4;
                break;
            case "Why?":
                index = 5;
                break;
        }
        return index;

    }


    //creating story using previous methods
    //removes used words from the Map

    public String[] creatingTheStory(String[] keys) {
        String[] storyPlayer = new String[keys.length];
        String key;
        for (int i = 0; i < keys.length; i++) {
            key = keys[i];
            String answerOneWord = getWordFromList(key);
            storyPlayer[indexOfAnswer(key)] = answerOneWord;
            getAnswerList(key).remove(answerOneWord);
        }
        return storyPlayer;
    }


    //printing story as string
    //getting the value - String[] theStory in Main, because each player story is saved in String[]
    public void printStory(String[] story) {
        String storyInString = "";
        for (int i = 0; i < story.length; i++) {
            if (!story[i].isEmpty()) {
                if (i == 0) {
                    String formattedWord = story[i];
                    formattedWord = formattedWord.substring(0, 1).toUpperCase() + formattedWord.substring(1).toLowerCase();
                    storyInString += ("" + formattedWord + " ");
                    setColor();
                    System.out.print(formattedWord + " " + ANSI_RESET);
                } else {
                    String formattedWord = story[i];
                    formattedWord = formattedWord.substring(0, 1).toLowerCase() + formattedWord.substring(1).toLowerCase();
                    storyInString += ("" + formattedWord + " ");
                    setColor();
                    System.out.print(formattedWord + " " + ANSI_RESET);
                }

            } else {
                setColor();
                System.out.print("  ...  " + ANSI_RESET);

            }
        }
        System.out.println("\n");
    }
//        String formattedStory = storyInString.substring(0, 1).toUpperCase() + storyInString.substring(1).toLowerCase();
//        System.out.println(formattedStory+"\n");
    }

