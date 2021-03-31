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
        //Map holding final results: story for each player
        Map<String, String[]> resultOfGameFinalStory = new HashMap<>();


        Functionality functionality = new Functionality();
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
            int player = 1; //always is 1 player
            String answer;
            String playerName;
            //Asking questions - getting answers; answers stored in MAP in functionality
            String[] playerStory;
            do {
                System.out.println("Enter player name: ");
                playerName = scanner.next();
//                int indexForPlayerName = 0;

                System.out.println("Player " + player + " give your answers: ");
                for (int i = 0; i < keys.length; i++) {
                    answer = functionality.askQuestion(keys[i]);
                    functionality.addAnswer(keys[i], answer);
                }

//                answer = functionality.askQuestion(who);
//                functionality.addAnswer(who, answer);
//                answer = functionality.askQuestion(withWho);
//                functionality.addAnswer(withWho, answer);
//                answer = functionality.askQuestion(when);
//                functionality.addAnswer(when, answer);
//                answer = functionality.askQuestion(where);
//                functionality.addAnswer(where, answer);
//                answer = functionality.askQuestion(didWhat);
//                functionality.addAnswer(didWhat, answer);
//                answer = functionality.askQuestion(why);
//                functionality.addAnswer(why, answer);


                player += 1;
            } while (player < givenPlayers + 1);

            player = 1;
            do {
                //creating story
                playerStory = functionality.creatingTheStory(keys);
//            functionality.printStory(playerStory);
                resultOfGameFinalStory.put(playerName, playerStory);
//            player += 1;
                //System.out.println("Map content after STORY: " + functionality.answers.toString());
                System.out.println(resultOfGameFinalStory.toString());
                Database.insertIntoTable(connection, playerName, playerStory);
                player++;
            } while (player < givenPlayers + 1);
//            System.out.println("Map content before THE STORY: " + functionality.answers.toString());


            //Printing results for game
            player = 1;
            for (int i = 0; i < givenPlayers; i++) {
                System.out.println("Story for Player: " + player);
                String[] story = resultOfGameFinalStory.get(player);
                functionality.printStory(story);
                player++;
            }


        }
    }
}
