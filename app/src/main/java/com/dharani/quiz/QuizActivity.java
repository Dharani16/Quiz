package com.dharani.quiz;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Arrays;

public class QuizActivity extends AppCompatActivity {

    TextView question;
    RadioGroup answers;
    RadioButton answer1, answer2, answer3, answer4;
    Button btNext;
    int quesIndex = 0;
    int selected[] = null;
    int correctAns[] = null;
    boolean review = false;
    Intent menu = null;
    BufferedReader bReader = null;
    static JSONArray quesList = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);

        question = (TextView)findViewById(R.id.textView_question);
        answers = (RadioGroup)findViewById(R.id.radioGroup);
        answer1 = (RadioButton)findViewById(R.id.optionOne);
        answer2 = (RadioButton)findViewById(R.id.optionTwo);
        answer3 = (RadioButton)findViewById(R.id.optionThree);
        answer4 = (RadioButton)findViewById(R.id.optionFour);

        btNext = (Button)findViewById(R.id.button_next);
        loadQuestion();
        showQuestion(quesIndex);
        btNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(QuizActivity.this, "Move to next question", Toast.LENGTH_SHORT).show();
                setAnswer();
                quesIndex++;
                if (quesIndex >= getQuesList().length()){
                    quesIndex = getQuesList().length()-1;
                }

                
            }
        });
    }

    private void loadQuestion() {
        try {
            InputStream questions = getResources().openRawResource(R.raw.questions);
            bReader = new BufferedReader(new InputStreamReader(questions));
            StringBuilder quesString = new StringBuilder();
            String aJsonLine = null;
            while ((aJsonLine = bReader.readLine()) != null) {
                quesString.append(aJsonLine);
            }
            Log.d(this.getClass().toString(), quesString.toString());
            JSONObject quesObj = new JSONObject(quesString.toString());
            //quesList -> JSONArray
            quesList = quesObj.getJSONArray("Questions");
            Log.d(this.getClass().getName(),
                    "Num Questions " + quesList.length());
        } catch (Exception e) {

        } finally {
            try {
                bReader.close();
            } catch (Exception e) {
                Log.e("", e.getMessage().toString(), e.getCause());
            }
        }
    }

    public static JSONArray getQuesList(){
        return quesList;
    }


    private void showQuestion(int qIndex) {
        try{
            JSONObject aQues = getQuesList().getJSONObject(qIndex);
            String quesValue = aQues.getString("Question");
            if (correctAns[qIndex] == -1){
                String correctAnsStr = aQues.getString("CorrectAnswer");
                correctAns[qIndex] = Integer.parseInt(correctAnsStr);
            }
            question.setText(quesValue.toCharArray(), 0, quesValue.length());
            answers.check(-1);
            JSONArray ansList = aQues.getJSONArray("Answers");
            String aAns = ansList.getJSONObject(0).getString("Answer");
            answer1.setText(aAns.toCharArray(), 0, aAns.length());
            aAns = ansList.getJSONObject(1).getString("Answer");
            answer2.setText(aAns.toCharArray(), 0, aAns.length());
            aAns = ansList.getJSONObject(2).getString("Answer");
            answer3.setText(aAns.toCharArray(), 0, aAns.length());
            aAns = ansList.getJSONObject(3).getString("Answer");
            answer4.setText(aAns.toCharArray(), 0, aAns.length());
            Log.d("",selected[qIndex]+"");





        }catch (Exception e){

        }
    }

    private void setAnswer() {
        if (answer1.isChecked())
            selected[quesIndex] = 0;
        if (answer2.isChecked())
            selected[quesIndex] = 1;
        if (answer3.isChecked())
            selected[quesIndex] = 2;
        if (answer4.isChecked())
            selected[quesIndex] = 3;

        Log.d("", Arrays.toString(selected));
        Log.d("",Arrays.toString(correctAns));
    }


}
