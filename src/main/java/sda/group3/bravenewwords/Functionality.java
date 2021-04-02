package sda.group3.bravenewwords;

import java.util.*;

public class Functionality {

    // creating a map where save answers to specific questions
    Map<String, List<String>> answers = new HashMap<>();

    //asking a question and saving the answer
    public String askQuestion(String question) {
        Scanner scanner = new Scanner(System.in);
        System.out.println(question);
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
    public void printStory (String[]story){
        String storyInString = "";
        for (int i = 0; i < story.length; i++) {
            if (story[i]!=null){
                storyInString += (story[i] + " ");
            }
        }
        String formattedStory = storyInString.substring(0,1).toUpperCase() + storyInString.substring(1).toLowerCase();
        System.out.println(formattedStory + "\n");
    }
}
