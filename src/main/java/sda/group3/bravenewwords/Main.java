package sda.group3.bravenewwords;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.*;

public class Main {
    public static void main(String[] args) throws SQLException {
        //1. choosing how many players (3-4) -

        // 2.questions - viena fukncija kas uzdod jautājumu jautājums tiek izvēlēts randomā
        // 3. answers - collecting and saving to concrete question (maps - key- questions, value <list>- answers)
        //3.5. collecting answers and checking with whitelist, and then saving if they are okey

        //4.generating "story" ->

        // 5.show story, probably "for"(as separate method (if we have time)- formating that all words is small letters, spacing)

        // 6.database for "story", unformated story in database

        // 7. rerun programm - option tho choose if want to play again

        //Definē nemaināmu variabli, lai nav visur jāpārraksta
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
        //MAP HOLDING FINAL RESULTS: story for each player, key = playersName[indexForPlayerName]
        Map<String, String[]> resultOfGameFinalStory = new HashMap<>();

        Functionality functionality = new Functionality();


        //TO DATABASE
        Connection connection = Database.getConnection();
        if (connection == null) {
            System.out.println("We ain't able to connect to the database");
        } else {
            System.out.println("IR");
            Database.createTable(connection);


            System.out.println("Please write your answer to question:");

            //Asking how many players
            Scanner scanner = new Scanner(System.in);
            System.out.println("Enter Player Number: ");
            int givenPlayers = scanner.nextInt();
            int player = 1; //always there is 1 player
            String answer;

            ///NEW VARIABLES - for getting each players name

            String[] playerNames = new String[givenPlayers];
            int indexForPlayerName = 0;


            //GETTING ANSWERS
            String[] playerStory;
            do {
                System.out.println("Enter player name: ");

                //NEW asking players name; adding it to the String Array outside this loop
                //it's made so that Player 1 = entered name, and it can be shown at the end

                String enteredName = scanner.next();
                playerNames[indexForPlayerName] = enteredName;


               //Asking questions - getting answers; answers stored in MAP in Functionality
                System.out.println("Player " + player + " give your answers: ");
                for (int i = 0; i < keys.length; i++) {
                    answer = functionality.askQuestion(keys[i]);
                    functionality.addAnswer(keys[i], answer);
                }
                player += 1;
                indexForPlayerName++;
            } while (player < givenPlayers + 1);

            //For TESTS
            //System.out.println("Map content BEFORE STORY: " + functionality.answers.toString());

            //Resetting values for new loop
            player = 1;
            indexForPlayerName = 0;

            //CREATING RANDOM :) story
            do {
                playerStory = functionality.creatingTheStory(keys);
                resultOfGameFinalStory.putIfAbsent(playerNames[indexForPlayerName], playerStory);
                //FOR TESTS:
                // System.out.println("Map content after STORY: " + functionality.answers.toString());
                //System.out.println("Second-FINAL  MAP:  "+resultOfGameFinalStory.toString());
                Database.insertIntoTable(connection, playerNames[indexForPlayerName], playerStory);
                player++;
                indexForPlayerName++;
            } while (player < givenPlayers + 1);

            //Resetting values for next loop;
            player = 1;
            indexForPlayerName=0;


            //PRINTING results for game
            for (int i = 0; i < givenPlayers; i++) {
                System.out.println("Story for Player " + player+":  "+playerNames[indexForPlayerName]);
                String[] story = resultOfGameFinalStory.get(playerNames[indexForPlayerName]);
                functionality.printStory(story);
                player++;
                indexForPlayerName++;
            }

//HAPPY END !
        }
    }
}
