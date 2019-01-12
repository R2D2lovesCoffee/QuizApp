package com.example.aeroz.quizzapp;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.aeroz.quizzapp.notActivities.HttpRequestMaker;
import com.example.aeroz.quizzapp.notActivities.Question;
import com.example.aeroz.quizzapp.notActivities.Quiz;
import com.example.aeroz.quizzapp.notActivities.Student;
import com.example.aeroz.quizzapp.notActivities.TakenQuiz;
import com.example.aeroz.quizzapp.notActivities.TakenQuizDB;
import com.google.gson.Gson;

import java.util.Collections;
import java.util.List;

public class SQuestionActivity extends AppCompatActivity {

    private ImageView imageView;
    private Button button;
    private TextView textViewNoQuestion;
    private TextView textViewQuestion;
    private TextView[] textViewsAnswers = new TextView[4];
    private Student student;
    private Quiz quiz;
    private float score=0;
    private int noCorrect=0;
    private int counter=0;
    private boolean checked[] = new boolean[4];
    private Question question;
    private TakenQuizDB takenQuizDB;
    private boolean correct=true;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_squestion);
        //INITIALIZE FIELDS
        for(int i = 0;i<4;i++)
            checked[i]=false;
        imageView = findViewById(R.id.imgView_squestion_ic_close);
        button = findViewById(R.id.btn_squestion_continue);
        textViewNoQuestion = findViewById(R.id.txtView_squestion_noquestion);
        textViewQuestion = findViewById(R.id.txtView_squestion_question);
        textViewsAnswers[0] = findViewById(R.id.txtView_squestion_ans1);
        textViewsAnswers[1] = findViewById(R.id.txtView_squestion_ans2);
        textViewsAnswers[2] = findViewById(R.id.txtView_squestion_ans3);
        textViewsAnswers[3] = findViewById(R.id.txtView_squestion_ans4);
        student = (Student)getIntent().getExtras().getSerializable("student");
        quiz = (Quiz)getIntent().getExtras().getSerializable("quiz");
        takenQuizDB = (TakenQuizDB)getIntent().getExtras().getSerializable("takenQuizDB");
//        Collections.shuffle(quiz.getQuestions());
        question = quiz.getQuestions().get(0);
        ////////////////////
        displayCurrent();
        textViewsAnswers[0].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checked[0]=!checked[0];
                if(checked[0])
                    textViewsAnswers[0].setTextColor(getResources().getColor(R.color.colorAccent));
                else
                    textViewsAnswers[0].setTextColor(getResources().getColor(R.color.white));
            }
        });
        textViewsAnswers[1].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checked[1]=!checked[1];
                if(checked[1])
                    textViewsAnswers[1].setTextColor(getResources().getColor(R.color.colorAccent));
                else
                    textViewsAnswers[1].setTextColor(getResources().getColor(R.color.white));
            }
        });
        textViewsAnswers[2].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checked[2]=!checked[2];
                if(checked[2])
                    textViewsAnswers[2].setTextColor(getResources().getColor(R.color.colorAccent));
                else
                    textViewsAnswers[2].setTextColor(getResources().getColor(R.color.white));
            }
        });
        textViewsAnswers[3].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checked[3]=!checked[3];
                if(checked[3])
                    textViewsAnswers[3].setTextColor(getResources().getColor(R.color.colorAccent));
                else
                    textViewsAnswers[3].setTextColor(getResources().getColor(R.color.white));
            }
        });
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(counter+1==quiz.getQuestions().size()){
                    score = (float)noCorrect/quiz.getQuestions().size();
                    String[] scr=new String[2];scr = String.format("%.2f",score).split(",");
                    score=Float.parseFloat(scr[0]+"."+scr[1])*100;
                    takenQuizDB.setScore(score);
                    new HttpRequestMaker().execute("POST","http://188.25.199.62:8000/takenQuizez",
                            new Gson().toJson(takenQuizDB));
                    startActivity(new Intent(SQuestionActivity.this,SResultActivity.class)
                    .putExtra("student",student).putExtra("score",score).putExtra("quiz",quiz)
                    .putExtra("noCorrect",noCorrect));
                    Log.d("pandaaa", "onClick: "+takenQuizDB);
                    Log.d("pandaaa", "onClick: "+noCorrect);
                    Log.d("pandaaa", "onClick: "+score);
                }
                else{
                    //verifyAnswer();
                    for(int i = 0;i<4;i++) {
                        if((question.getAnswers().get(i).getIsCorect()&&!checked[i])||(!question.getAnswers().get(i).getIsCorect()&&checked[i]))
                            correct = false;
                    }
                    if(correct)
                        noCorrect++;
                    Log.d("panda", "onClick: "+noCorrect);
                    counter++;
                    resetQuestion();
                    displayCurrent();
                }
            }
        });
    }
    public void displayCurrent(){
        textViewNoQuestion.setText(""+(counter+1)+"/"+quiz.getQuestions().size());
        textViewQuestion.setText(quiz.getQuestions().get(counter).getText());
        for(int i = 0;i<quiz.getQuestions().get(i).getAnswers().size();i++)
            textViewsAnswers[i].setText(quiz.getQuestions().get(counter).getAnswers().get(i).getText());
    }
    public void resetQuestion(){
        correct = true;
        question = quiz.getQuestions().get(counter);
        for(int i = 0;i<quiz.getQuestions().get(i).getAnswers().size();i++){
            textViewsAnswers[i].setTextColor(getResources().getColor(R.color.white));
            checked[i] = false;
        }
    }
}
