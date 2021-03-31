package sda.group3.bravenewwords;

import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
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


        Functionality functionality = new Functionality();
        //Asking questions - getting answers
        //FOR TEST: PLAYER 1 - 1,2,3,4,5,6 || PLAYER 2 - A,B,C,D,E,F
        //          to check if the order of words used in story is correct
        System.out.println("Please write your answer to question:");
        System.out.println("Player 1 answers: ");
        String answer = functionality.askQuestion(who);
        functionality.addAnswer(who, answer);
        answer = functionality.askQuestion(withWho);
        functionality.addAnswer(withWho, answer);
        answer = functionality.askQuestion(when);
        functionality.addAnswer(when, answer);
        answer = functionality.askQuestion(where);
        functionality.addAnswer(where, answer);
        answer = functionality.askQuestion(didWhat);
        functionality.addAnswer(didWhat, answer);
        answer = functionality.askQuestion(why);
        functionality.addAnswer(why, answer);
        System.out.println("Player 2 answers:  ");
        answer = functionality.askQuestion(who);
        functionality.addAnswer(who, answer);
        answer = functionality.askQuestion(withWho);
        functionality.addAnswer(withWho, answer);
        answer = functionality.askQuestion(when);
        functionality.addAnswer(when, answer);
        answer = functionality.askQuestion(where);
        functionality.addAnswer(where, answer);
        answer = functionality.askQuestion(didWhat);
        functionality.addAnswer(didWhat, answer);
        answer = functionality.askQuestion(why);
        functionality.addAnswer(why, answer);

        //for test
        System.out.println("Map content before THE STORY: " + functionality.answers.toString());


        //creating story for Player 1
        functionality.addWordToStory(who);
        functionality.addWordToStory(withWho);
        functionality.addWordToStory(when);
        functionality.addWordToStory(where);
        functionality.addWordToStory(didWhat);
        functionality.addWordToStory(why);
        String[] storyForPlayerOne = functionality.theStory;

        //for test
        System.out.println("Map content AFTER PLAYER 1: " + functionality.answers.toString());

        System.out.println("Story for Player One: ");
        functionality.printStory(storyForPlayerOne);

        //creating story for Player 2
        functionality.addWordToStory(who);
        functionality.addWordToStory(withWho);
        functionality.addWordToStory(when);
        functionality.addWordToStory(where);
        functionality.addWordToStory(didWhat);
        functionality.addWordToStory(why);
        String[] storyForPlayerTwo = functionality.theStory;

        System.out.println("Story for Player Two: ");
        functionality.printStory(storyForPlayerTwo);
        //for test - to see that map is empty
        System.out.println("Map content END OF GAME: " + functionality.answers.toString());


//        String answer = functionality.askQuestion("Who? / What?");
//        functionality.addAnswer("Who? / What?", answer);
//        answer = functionality.askQuestion("With whom / what?");
//        functionality.addAnswer("With whom / what?", answer);
//        answer = functionality.askQuestion("When?");
//        functionality.addAnswer("When?", answer);
//        answer = functionality.askQuestion("Where?");
//        functionality.addAnswer("Where?", answer);
//        answer = functionality.askQuestion("Did what?");
//        functionality.addAnswer("Did what?", answer);
//        answer = functionality.askQuestion("Why?");
//        functionality.addAnswer("Why?", answer);

//        answer = functionality.askQuestion("Who? / What?");
//        functionality.addAnswer("Who? / What?", answer);
//        answer = functionality.askQuestion("With whom / what?");
//        functionality.addAnswer("With whom / what?", answer);
//        answer = functionality.askQuestion("When?");
//        functionality.addAnswer("When?", answer);
//        answer = functionality.askQuestion("Where?");
//        functionality.addAnswer("Where?", answer);
//        answer = functionality.askQuestion("Did what?");
//        functionality.addAnswer("Did what?", answer);
//        answer = functionality.askQuestion("Why?");
//        functionality.addAnswer("Why?", answer);


    }
}
