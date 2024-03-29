package com.example.iqwhizz.Objects;

public class Answer {

    private int answerID;

    private byte[] image; //path

    private String text;

    private int score ;

    /*
        Constructeur
     */
    public Answer(int answerID, byte[] image, String text, int score)
    {
        this.answerID=answerID;
        this.image=image;
        this.text=text;
        this.score=score;
    }

    public byte[] getImage()
    {
        return image;
    }

    public int getScore()
    {
        return score;
    }

    public String getText()
    {
        return text;
    }

    public int getAnswerID()
    {
        return answerID;
    }

    public void setAnswerID(int answerID)
    {
        this.answerID = answerID;
    }

    /*
        true si reponse correct false sinon
     */
    public boolean isRight()
    {
        return (this.score!=0);
    }
}
