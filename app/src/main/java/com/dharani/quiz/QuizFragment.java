package com.dharani.quiz;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

public class QuizFragment extends Fragment {

    private Button btQuizOne, btQuizTwo, btQuizThree, btQuizFour;

    public QuizFragment() {

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_quiz, container, false);
        btQuizOne = (Button) view.findViewById(R.id.button_quiz);
        btQuizTwo = (Button) view.findViewById(R.id.button_quiz2);
        btQuizThree = (Button) view.findViewById(R.id.button_quiz3);
        btQuizFour = (Button) view.findViewById(R.id.button_quiz4);

        btQuizOne.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(getActivity(), "Quiz one is clicked", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getActivity(),QuizActivity.class);
                startActivity(intent);
            }
        });

        btQuizTwo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(), "Quiz two is clicked", Toast.LENGTH_SHORT).show();
            }
        });

        btQuizThree.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(), "Quiz three is clicked", Toast.LENGTH_SHORT).show();
            }
        });

        btQuizFour.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(), "Quiz four is clicked", Toast.LENGTH_SHORT).show();
            }
        });
        return view;
    }


}
