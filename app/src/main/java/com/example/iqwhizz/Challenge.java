package com.example.iqwhizz;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.iqwhizz.DAO.QuestionDAO;
import com.example.iqwhizz.DAO.TestDAO;
import com.example.iqwhizz.Objects.Answer;
import com.example.iqwhizz.Objects.Question;
import com.example.iqwhizz.Objects.Test;

import org.w3c.dom.Text;

public class Challenge extends AppCompatActivity {

    private TextView title;
    private TextView textQuestion;
    private TextView textAnswer1;
    private TextView textAnswer2;
    private TextView textAnswer3;
    private TextView textAnswer4;

    private CardView cardAnswer1;
    private CardView cardAnswer2;
    private CardView cardAnswer3;
    private CardView cardAnswer4;

    Test currentTest;
    Question currentQuestion;
    Answer[] currentAnswer;

    private byte maxNbQuestion;
    private byte currentNbQuestion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //WIP
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_challenge);
        String type = getIntent().getStringExtra("type");
        if(type.equals("Long (40 questions)")){
            maxNbQuestion = 40;
        }else{
            maxNbQuestion = 5;
        }


        currentNbQuestion = 1;
        String category = getIntent().getStringExtra("category");

        //TEMPORAIRE, Challenge Init doit donné un Test a Challenge
        this.currentTest = TestDAO.getTest(1);

        title = findViewById(R.id.questionNb);
        title.setText("Question #" + currentNbQuestion +"/" + maxNbQuestion );

        textQuestion = findViewById(R.id.question_text);
        textAnswer1 = findViewById(R.id.answer_text1);
        textAnswer2 = findViewById(R.id.answer_text2);
        textAnswer3 = findViewById(R.id.answer_text3);
        textAnswer4 = findViewById(R.id.answer_text4);

        cardAnswer1 = findViewById(R.id.answer_card1);
        cardAnswer2 = findViewById(R.id.answer_card2);
        cardAnswer3 = findViewById(R.id.answer_card3);
        cardAnswer4 = findViewById(R.id.answer_card4);


        currentQuestion = QuestionDAO.getQuestion(currentTest.getCurrentQuestionID());
        currentAnswer = currentQuestion.getAnswers();

        textQuestion.setText(currentQuestion.getText());

        textAnswer1.setText(currentAnswer[0].getText());
        textAnswer2.setText(currentAnswer[1].getText());
        textAnswer3.setText(currentAnswer[2].getText());
        textAnswer4.setText(currentAnswer[3].getText());

        setupListener();

    }

    private void setupListener(){

        cardAnswer1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                answered(textAnswer1.getText().toString());
            }
        });
        cardAnswer2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                answered(textAnswer2.getText().toString());
            }
        });
        cardAnswer3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                answered(textAnswer3.getText().toString());
            }
        });
        cardAnswer4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                answered(textAnswer4.getText().toString());
            }
        });

    }

    private void answered(String answer){
        int duration = Toast.LENGTH_SHORT;
        Context context = getApplicationContext();
        Toast toast = Toast.makeText(context, answer, duration);
        toast.show();

        next();

    }

    private void next() {
        currentNbQuestion++;
        title.setText("Question #" + currentNbQuestion +"/" + maxNbQuestion );

        if(currentNbQuestion>5){

            title.setText("WIP Résultat");
        }
    }
}