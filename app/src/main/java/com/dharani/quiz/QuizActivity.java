package com.dharani.quiz;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Arrays;

public class QuizActivity extends AppCompatActivity {
    TextView questionno;
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
    int score = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);

        questionno = (TextView) findViewById(R.id.questionno);
        question = (TextView) findViewById(R.id.textView_question);
        answers = (RadioGroup) findViewById(R.id.radioGroup);
        answer1 = (RadioButton) findViewById(R.id.optionOne);
        answer2 = (RadioButton) findViewById(R.id.optionTwo);
        answer3 = (RadioButton) findViewById(R.id.optionThree);
        answer4 = (RadioButton) findViewById(R.id.optionFour);
        btNext = (Button) findViewById(R.id.button_next);

        loadQuestion();

        btNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(QuizActivity.this, "Move to next question", Toast.LENGTH_SHORT).show();
                setAnswer();
                quesIndex++;
                /*if (quesIndex >getQuesList().length()) {
                    quesIndex = getQuesList().length() - 1;
                }*/

                showQuestion(quesIndex);

            }
        });

        selected = new int[getQuesList().length()];
        Arrays.fill(selected, -1);
        correctAns = new int[getQuesList().length()];
        Arrays.fill(correctAns, -1);
//        questionno.setText(1 + "/" + getQuesList().length());
        showQuestion(quesIndex);

    }

    private void loadQuestion() {
        try {
            InputStream questions = getResources().openRawResource(R.raw.questions);
            bReader = new BufferedReader(new InputStreamReader(questions));
            StringBuilder quesString = new StringBuilder();
            String aJsonLine;
            while ((aJsonLine = bReader.readLine()) != null) {
                quesString.append(aJsonLine);
            }
            Log.d(this.getClass().toString(), quesString.toString());
            JSONObject quesObj = new JSONObject(quesString.toString());

            //quesList -> JSONArray
            quesList = quesObj.getJSONArray("Questions");
            Toast.makeText(QuizActivity.this, "qcount"+quesList.length(), Toast.LENGTH_SHORT).show();
//            Log.d(this.getClass().getName(), "Num Questions " + quesList.length());
        } catch (Exception e) {

        } finally {
            try {
                bReader.close();
            } catch (Exception e) {
                Log.e("", e.getMessage().toString(), e.getCause());
            }
        }
    }

    public static JSONArray getQuesList() {
        return quesList;
    }


    private void showQuestion(int qIndex) {
        // initially qIndex have 0
        try {
            JSONObject aQues = getQuesList().getJSONObject(qIndex);
            String quesValue = aQues.getString("Question");
            if (correctAns[qIndex] == -1) {
                String correctAnsStr = aQues.getString("CorrectAnswer");
                correctAns[qIndex] = Integer.parseInt(correctAnsStr);

            }

            question.setText(quesValue.toCharArray(), 0, quesValue.length());
            questionno.setText((qIndex + 1) + "/" + getQuesList().length());

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
            Log.d("", selected[qIndex] + "");
            Toast.makeText(QuizActivity.this, "qno"+quesIndex, Toast.LENGTH_SHORT).show();
            Toast.makeText(this, "tot"+quesList.length(), Toast.LENGTH_SHORT).show();
            //change button name
            if ((quesIndex) == (quesList.length())) {
                Log.d("score", "score=" + quesIndex);
                btNext.setText("Done");
                Toast.makeText(this, "Move to SCORE Result", Toast.LENGTH_SHORT).show();
                setAnswer();
                //Calculate Score
                Log.d("oldscore", "oldscore=" + score);
                //correctAns.length = 7

                for (int i = 0; i < correctAns.length; i++) {
                    if ((correctAns[i] != -1) && (correctAns[i] == selected[i]))
                        score++;
                }

                Log.d("score", "score=" + score);
                Toast.makeText(QuizActivity.this, "Socre = " + score, Toast.LENGTH_SHORT).show();
                btNext.setText("Done");

                AlertDialog alertDialog;
                alertDialog = new AlertDialog.Builder(this).create();
                alertDialog.setTitle("Score Guard Sheet");
                alertDialog.setMessage((score) + " out of " + (getQuesList().length()));
//                alertDialog.setCancelable(false);
                alertDialog.show();

            }
        } catch (Exception e) {}
    }


    private void setAnswer() {
        if(quesIndex==getQuesList().length())
            quesIndex--;
        if (answer1.isChecked())
            selected[quesIndex] = 0;
        if (answer2.isChecked())
            selected[quesIndex] = 1;
        if (answer3.isChecked())
            selected[quesIndex] = 2;
        if (answer4.isChecked())
            selected[quesIndex] = 3;
        //Toast.makeText(getApplicationContext()," user selected value is"+Arrays.toString(selected),Toast.LENGTH_LONG).show();
        //Toast.makeText(getApplicationContext(),"correct answer is"+Arrays.toString(correctAns),Toast.LENGTH_LONG).show();
        //Toast.makeText(this, "correctAns length "+correctAns.length, Toast.LENGTH_SHORT).show();
        Log.d("selected value is", Arrays.toString(selected));
        Log.d("Correct answer is ", Arrays.toString(correctAns));
    }


}
