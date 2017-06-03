package com.dharani.quiz;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * Created by LENOVO on 06/03/17.
 */

public class QuestionActivity extends Activity{

    Intent menu = null;
    BufferedReader bReader = null;
    static JSONArray quesList = null;

    public void loadQuestions() throws Exception {
        try {
            InputStream questions = this.getBaseContext().getResources().openRawResource(R.raw.questions);
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

}
