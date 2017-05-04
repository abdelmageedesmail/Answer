package com.team.answer.models;

/**
 * Created by javawy on 5/4/17.
 */

public class QueData {
    private String question;
    private String[] choices;
    private String answer;
    private String level;
    public QueData(String question, String[] choices, String answer, String level) {
        this.question = question;
        this.choices = choices;
        this.answer = answer;
        this.level = level;
    }


    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String[] getChoices() {
        return choices;
    }

    public void setChoices(String[] choices) {
        this.choices = choices;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    @Override
    public String toString() {
        return question;
    }
}
