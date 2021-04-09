package sda.group3.bravenewwords;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.*;

public class Main {
    public static void main(String[] args) throws SQLException {
        //1. choosing how many players (3-4) -

        // 2.questions - viena fukncija kas uzdod jautājumu
        // 3. answers - collecting and saving answers to specific question (maps - key- questions, value <list>- answers)
        //3.5. collecting answers and checking with whitelist, and then saving if they are okey

        //4.generating "story" ->

        // 5.show story, probably "for"(as separate method (if we have time)- formating that all words is small letters, spacing)

        // 6.database for "story", unformated story in database

        // 7. rerun programm - option tho choose if want to play again
        String playAgain = "y";

        Functionality functionality = new Functionality();

        //stores game questions in String array
        final String who = "Who? / What?";
        final String withWho = "With whom / with what?";
        final String didWhat = "Did what?";
        final String where = "Where?";
        final String when = "When?";
        final String why = "Why?";
        String[] keys = new String[6];
        keys[0] = who;
        keys[1] = withWho;
        keys[2] = didWhat;
        keys[3] = where;
        keys[4] = when;
        keys[5] = why;

//        // stores which player's data we are working with
//        int player = 1;
//
//        // stores player's answer to specific question
//        String answer;
//
//        // stores player's final story
//        String[] playerStory;
//
//        //stores all final stories of all players / key = playerName, value = playerStory
//        Map<String, String[]> resultOfGameFinalStory = new HashMap<>();


        //Connect to database for storing answers and accessing whitelist
        Connection connection = Database.getConnection();

        if (connection == null) {
            System.out.print("\u26D4 ");
            functionality.printingErrorText("Sorry, we were not able to connect to the database.");
        } else {
            Database.createTable(connection);
            do {

                int player = 1;

                // stores player's answer to specific question
                String answer;

                // stores player's final story
                String[] playerStory;

                //stores all final stories of all players / key = playerName, value = playerStory
                Map<String, String[]> resultOfGameFinalStory = new HashMap<>();

                functionality.printingMainText("\t\t   Hello, welcome to  \t\t");
                functionality.printingMainText("\t\t   Brave New Words!\u00A9  \t\t");
                functionality.pause(444499);
                functionality.printingMainText("We have only 3 rules here: ");
                functionality.printingMainText("Rule number one - we don't talk about user choices!");
                functionality.printingMainText("Rule number two - we don't judge your choices! (jk)");
                functionality.printingMainText("Rule number three - no shirts, no shoes, no shame.");


                //Asking how many players will play
                Scanner scanner = new Scanner(System.in);
                System.out.println();
                functionality.printingMainText("2 - 5 players allowed, ");
                functionality.printingMainText("\u2757 if you're here with a bigger gang, please look elsewhere. (╹◡╹)");
                functionality.pause(999999);
                functionality.printingMainText("\u27A4 How many players will play the game ?");


                boolean validInput = false;
                int givenPlayers = 0;
                while (!validInput) {
                    try {
                        givenPlayers = scanner.nextInt();
                        if (givenPlayers > 1 && givenPlayers <= 5) {
                            validInput = true;
                        } else if (givenPlayers == 1) {
                            System.out.print("\u26D4 ");
                            functionality.printingErrorText("\tOh, you loner, please get some friends!");
                            functionality.printingErrorText("When you have, enter a number from 2 to 5 to proceed.");
                            functionality.printingErrorText("P.S. Imaginary friends count as well. (╹◡╹)");
                        } else {
                            System.out.print("\u26D4 ");
                            functionality.printingErrorText("Please enter the number of players (from 2 to 5)");


                        }

                    } catch (InputMismatchException e) {
                        System.out.print("\u26D4 ");
                        functionality.printingErrorText("Please enter a number from 2 - 5! Please do not use letters. It's really not that difficult.");
                        scanner.next();
                    }
                }


                /// stores players' names
                String[] playerNames = new String[givenPlayers];
                int indexForPlayerName = 0;

//resetting color variables
                functionality.emptyListAndEmptyCopyString();

                //asking questions and saving answers

                do {
                    //asking player's name and adding it to the String Array
                    functionality.printingMainText("\u27A4 Please enter your name: ");

                    //new Scanner to fix problem = after nextINT() the nextLine() is not working,
                    // BUT next() takes only first string not all what is entered
                    Scanner scanner1 = new Scanner(System.in);
                    String enteredName = scanner1.nextLine();
                    while (Database.compareToWhitelist(connection, enteredName)) {
                        System.out.print("\u26D4 ");
                        functionality.printingErrorText("This is a BAD, BAD word and highly unlikely that's your actual name!");
                        functionality.printingMainText("\u27A4 Please enter a different name:");
                        enteredName = scanner1.nextLine();
                    }

                    playerNames[indexForPlayerName] = enteredName;

                    //asking questions, storing answers in the Map (Functionality Class)
                    functionality.printingMainText("Okay, let's get started! " +
                            enteredName.toUpperCase() + " please answer to the following questions: ");
                    for (int i = 0; i < keys.length; i++) {
                        answer = functionality.askQuestion(keys[i]);

                        // if word is in the whitelist, player has to insert new answer
                        while (Database.compareToWhitelist(connection, answer)) {
                            System.out.print("\u26D4 ");
                            functionality.printingErrorText("Someone's naughty. This is a BAD, BAD word! I know we said we don't judge, but please let's keep it PG friendly.");
                            answer = functionality.askQuestion(keys[i]);
                        }
                        //if word is not in the whitelist, answer is saved in the map
                        functionality.addAnswer(keys[i], answer);
                    }
                    player++;
                    indexForPlayerName++;

                    if (player < givenPlayers + 1) {
                        functionality.pause(">>>> loading questions for next player >>  ");
                    }
                } while (player < givenPlayers + 1);


                //Resetting values to create stories for every player
                player = 1;
                indexForPlayerName = 0;


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
                functionality.printingMainText("The colors are not accidental! One color for each!");
                functionality.pause();
                functionality.printingMainText("...so once upon a time...");
                functionality.pause();
                System.out.println();
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
                functionality.printingMainText("");
                playAgain = scanner.next();

                if (playAgain.equalsIgnoreCase("y")) {
                    functionality.pause(">>>> loading new game >>  ");
                }

            } while (playAgain.equalsIgnoreCase("y"));
//HAPPY END !
            functionality.printingMainText("Thank you for playing our game, see you some other time!");
            functionality.printingMainText("\u2728\u2728\u2728\u2728\u2728\u2728\u2728\u2728\u2728\u2728\u2728\u2728(╹◡╹)\u2728\u2728\u2728\u2728\u2728\u2728\u2728\u2728\u2728\u2728\u2728\u2728");
            System.out.println();
            System.out.println(functionality.ANSI_PURPLE+"\t\tthe end\t\t"+functionality.resetColor());
            System.out.println(functionality.ANSI_PURPLE+"Dina, Elīna, Karīna, Laura" +functionality.resetColor());
            ;

        }
    }
}
