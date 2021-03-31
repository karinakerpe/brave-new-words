package sda.group3.bravenewwords;

import java.util.*;

public class Functionality {

    Map<String, List<String>> answers = new HashMap<>();
    //FINAL RESULT  - STORY:

    //asking question, receiving answer
    public String askQuestion(String question) {
        Scanner scanner = new Scanner(System.in);
        System.out.println(question);
        String answer = scanner.nextLine();
        return answer;

    }

    //adding received answer to Map
    public void addAnswer(String question, String givenAnswer) {
        List<String> answer = answers.getOrDefault(question, new ArrayList<>());
        answer.add(givenAnswer);
        answers.put(question, answer);
    }

    //getting one List from Map, according given key(question)
    public List<String> getAnswerList(String key) {
        return answers.get(key);
    }


    //getting one word form List created in previous method, using Random
    public String getWordFromList(String key) {

        if (getAnswerList(key).size() == 1) {
            return getAnswerList(key).get(0);
        } else {
            Random rand = new Random();
            List<String> givenList = getAnswerList(key);
            int randomIndex = rand.nextInt(givenList.size());
            return givenList.get(randomIndex);
        }
    }

    //for sorting purposes for creating the final story,
    // creating new variable index according to question order
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


    //creating story, using previously created methods,
    // void - because result of method changes variable theStory /defined in beginning
    //removes form Map the word that are used

//    String[] theStory = new String[6];

    public String[] creatingTheStory(String[] keys) {
        String[] storyPlayer = new String[keys.length];
        String key;
        for (int i = 0; i < keys.length; i++) {
            key = keys[i];
            String answerOneWord = getWordFromList(key);
            storyPlayer[indexOfAnswer(key)] = answerOneWord;
            int indexForRemove = getAnswerList(key).indexOf(answerOneWord);
                answers.remove(key, getAnswerList(key).remove(indexForRemove));     }
        return storyPlayer;
    }


    //for printing String Array
    //getting the value - String[] theStory in Main,
    // because in Main creating new String[] for each Players story
    public void printStory (String[]story){
        for (int i = 0; i < story.length; i++) {
            if (story[i]!=null){
                System.out.printf(story[i] + " ");
            }
        }
        System.out.println("\n");
    }




}
