package sda.group3.bravenewwords;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.*;

public class Main {
    public static void main(String[] args) throws SQLException {
        Functionality functionality = new Functionality();
        //variables
        final String who = "Who? / What?";
        final String withWho = "With whom / with what?";
        final String didWhat = "Did what?";
        final String where = "Where?";
        final String when = "When?";
        final String why = "Why?";
        String playAgain;

        //stores game questions in String array
        String[] keys = new String[6];
        keys[0] = who;
        keys[1] = withWho;
        keys[2] = didWhat;
        keys[3] = where;
        keys[4] = when;
        keys[5] = why;


        //Connect to database for storing answers and accessing whitelist
        Connection connection = Database.getConnection();
        Scanner scanner = new Scanner(System.in);
        if (connection == null) {
            functionality.printingErrorText("Sorry, we were not able to connect to the database.");
        } else {
            Database.createTable(connection);
            do {
                int player = 1;
                // stores player answer to specific question
                String answer;
                // stores players final story
                String[] playerStory;
                //stores all final stories of all players / key = playerName, value = playerStory
                Map<String, String[]> resultOfGameFinalStory = new HashMap<>();

                //HELLO TEXT!
                functionality.helloAndRules();

                //Asking how many players will play
                functionality.addToSarcasticReplies();
                int givenPlayers = functionality.getGivenPlayers();

                // stores players' names
                String[] playerNames = new String[givenPlayers];
                int indexForPlayerName = 0;
                //resetting color variables
                functionality.emptyListAndEmptyCopyString();


                //asking questions and saving answers
                do {
                    //asking player's name and adding it to the String Array
                    functionality.printingMainText("\u27A4 Please enter your name: ");
                    String enteredName = functionality.gettingNameNotInWhiteList(connection);
                    playerNames[indexForPlayerName] = enteredName;

                    //asking questions, storing answers in the Map (Functionality Class)
                    functionality.printingMainText("Okay, let's get started! " +
                            enteredName.toUpperCase() + " please answer to the following questions: ");

                    for (String key : keys) {
                        answer = functionality.askQuestion(key);
                        // if word is in the whitelist, player has to insert new answer
                        answer = functionality.gettingAnswerNotInWhiteList(connection, answer, key);

                        //if word is not in the whitelist, answer is saved in the map
                        functionality.addAnswer(key, answer);
                    }
                    player++;
                    indexForPlayerName++;
                    functionality.resetStrike();
                    if (player < givenPlayers + 1) {
                        functionality.pause(">>>> loading questions for next player >>  ");
                    }
                } while (player < givenPlayers + 1);


                //Resetting values to create stories for every player
                player = 1;
                indexForPlayerName = 0;

                System.out.println(functionality.colorIndexForAnswerWords.toString());
                do {
                    //creating random story for each player
                    playerStory = functionality.creatingTheStory(keys);

                    // adding story to the Map of results
                    resultOfGameFinalStory.putIfAbsent(playerNames[indexForPlayerName], playerStory);

                    // saving story in the database
                    Database.insertIntoTable(connection, playerNames[indexForPlayerName], playerStory);
                    player++;
                    indexForPlayerName++;


                } while (player < givenPlayers + 1);
                //Resetting values to print the story for every player
                player = 1;
                indexForPlayerName = 0;

                functionality.pause(">>>> magic in progress >>");
                functionality.printingMainText("\t\t\tStory time!!!\t\t\t");
                functionality.pause();
                functionality.printingMainText("The colors are not accidental! One color for each player!");
                functionality.pause(33333);
                functionality.printingMainText("...so once upon a time...");
                functionality.pause();
                System.out.println();
                System.out.println(functionality.colorIndexForAnswerWords.toString());

                for (int i = 0; i < givenPlayers; i++) {
                    functionality.setColor(player);
                    System.out.println(playerNames[indexForPlayerName].toUpperCase() + " said that : " + functionality.resetColor());
                    System.out.println();
                    String[] story = resultOfGameFinalStory.get(playerNames[indexForPlayerName]);
                    functionality.printStory(story);

                    player++;
                    indexForPlayerName++;
                    functionality.pause();
                }


                functionality.printingMainText("Good job, the game is over. Would you like to play again?");
                System.out.println();
                functionality.printingMainText("\u27A4 press \u24E8 to play again / press 'any key' to exit.");
                playAgain = scanner.next();

                if (playAgain.equalsIgnoreCase("y")) {
                    functionality.pause(">>>> loading new game >>  ");
                }

            } while (playAgain.equalsIgnoreCase("y"));
        }
        functionality.printingMainText("(╹◡╹)");
        functionality.printingMainText("Wait! Due to your amazing answers you have received a special bonus!");
        functionality.printingMainText("Type one word and see all the stories that includes this word!");
        String searchWord = scanner.next();
        Database.printDatabaseRecord(connection, searchWord);

        functionality.endText();

    }
}
