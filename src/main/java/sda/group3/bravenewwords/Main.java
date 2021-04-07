package sda.group3.bravenewwords;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.*;
import java.util.concurrent.TimeUnit;

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

        Functionality functionality = new Functionality();

        //stores game questions in String array
        final String who = "Who? / What?";
        final String withWho = "With whom / what?";
        final String when = "When?";
        final String where = "Where?";
        final String didWhat = "Did what?";
        final String why = "Why?";
        String[] keys = new String[6];
        keys[0] = who;
        keys[1] = withWho;
        keys[2] = when;
        keys[3] = where;
        keys[4] = didWhat;
        keys[5] = why;

        // stores which player's data we are working with
        int player = 1;

        // stores player's answer to specific question
        String answer;

        // stores player's final story
        String[] playerStory;

        //stores all final stories of all players / key = playerName, value = playerStory
        Map<String, String[]> resultOfGameFinalStory = new HashMap<>();


        //Connect to database for storing answers and accessing whitelist
        Connection connection = Database.getConnection();

        if (connection == null) {
            functionality.setColorErrorText();
            System.out.println("We ain't able to connect to the database"+functionality.ANSI_RESET);
        } else {
            Database.createTable(connection);

            // Welcome text
            functionality.setColorMainText();
            System.out.println("Please write your answers to following question:" + functionality.ANSI_RESET);

            //Asking how many players will play
            Scanner scanner = new Scanner(System.in);
            functionality.setColor();
            System.out.println("Enter Number of Players: " + functionality.ANSI_RESET);

            boolean validInput = false;
            int givenPlayers = 0;
            while (!validInput) {
                try {
                    givenPlayers = scanner.nextInt();
                    if (givenPlayers > 0 && givenPlayers <= 5){
                        validInput = true;
                    } else {
                        System.out.println("Please enter a number of players ( from 1 to 5)");

                    }

                } catch (InputMismatchException e) {
                    System.out.println("Please enter a number from 1 - 5! Please do not use letters.");
                    scanner.next();
                }
            }


            /// stores players' names
            String[] playerNames = new String[givenPlayers];
            int indexForPlayerName = 0;


            //asking questions and saving answers

            do {
                //asking player's name and adding it to the String Array
                System.out.println(functionality.WHITE_BACKGROUND_BRIGHT + functionality.BLACK_BOLD_BRIGHT+"Enter player's name: " + functionality.ANSI_RESET);

                //new Scanner to fix problem = after nextINT() the nextLine() is not working,
                // BUT next() takes only first string not all what is entered
                Scanner scanner1 = new Scanner(System.in);
                String enteredName = scanner1.nextLine();
                playerNames[indexForPlayerName] = enteredName;

                //asking questions, storing answers in the Map (Functionality Class)
                functionality.setColorMainText();
                System.out.println("Player " + enteredName + " give your answers: " + functionality.ANSI_RESET);
                for (int i = 0; i < keys.length; i++) {
                    answer = functionality.askQuestion(keys[i]);

                    // if word is in the whitelist, player has to insert new answer
                    while (Database.compareToWhitelist(connection, keys[i], answer)) {
                        functionality.setColorErrorText();
                        System.out.println("This is a BAD, BAD word! Please insert something else"+functionality.ANSI_RESET);
                        answer = functionality.askQuestion(keys[i]);
                    }
                    //if word is not in the whitelist, answer is saved in the map
                    functionality.addAnswer(keys[i], answer);
                }
                player++;
                indexForPlayerName++;
// makes pause for 1 second
                try {

                    System.out.print(functionality.BLACK_BACKGROUND_BRIGHT+functionality.ANSI_BLACK+"  >>>> loading >>  "+functionality.ANSI_RESET);
                    for (int i = 0; i < 4; i++) {

                        TimeUnit.MICROSECONDS.sleep(999985);
                        System.out.print(functionality.BLACK_BACKGROUND_BRIGHT+functionality.ANSI_BLACK+"  >>  "+functionality.ANSI_RESET);
                    }
                    System.out.println("\n");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            } while (player < givenPlayers + 1);

            //For TESTS
            //System.out.println("Map content BEFORE STORY: " + functionality.answers.toString());

            //Resetting values to create stories for every player
            player = 1;
            indexForPlayerName = 0;


            do {
                //creating random story for each player
                playerStory = functionality.creatingTheStory(keys);

                // adding story to the Map of results
                resultOfGameFinalStory.putIfAbsent(playerNames[indexForPlayerName], playerStory);
                //FOR TESTS:
                // System.out.println("Map content after STORY: " + functionality.answers.toString());
                //System.out.println("Second-FINAL  MAP:  "+resultOfGameFinalStory.toString());

                // saving story in the database
                Database.insertIntoTable(connection, playerNames[indexForPlayerName], playerStory);
                player++;
                indexForPlayerName++;
            } while (player < givenPlayers + 1);

            //Resetting values to print the story for every player
            player = 1;
            indexForPlayerName = 0;


            for (int i = 0; i < givenPlayers; i++) {
                functionality.setColorMainText();
                System.out.println("Story for Player " + player + ":  " + playerNames[indexForPlayerName]
                        +"\t\t"+functionality.ANSI_RESET+"\n");
                String[] story = resultOfGameFinalStory.get(playerNames[indexForPlayerName]);
                functionality.printStory(story);
                player++;
                indexForPlayerName++;
            }

//HAPPY END !
        }
    }
}
