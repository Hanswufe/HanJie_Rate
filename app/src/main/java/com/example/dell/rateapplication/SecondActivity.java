package com.example.dell.rateapplication;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class SecondActivity extends AppCompatActivity {
    private final String TAG = "Second";
    TextView scoreA;
    TextView scoreB;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
        scoreA = (TextView) findViewById(R.id.scoreA);
        scoreB = (TextView) findViewById(R.id.scoreB);
        Button btnR = (Button) findViewById(R.id.btnR);
        btnR.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                scoreA.setText("0");
                scoreB.setText("0");
            }

        });
        Button btnA1 = (Button) findViewById(R.id.btnA1);
        btnA1.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                int scA = Integer.parseInt(scoreA.getText().toString())+3;
                scoreA.setText(""+scA);
            }

        });
        Button btnA2 = (Button) findViewById(R.id.btnA2);
        btnA2.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                int scA = Integer.parseInt(scoreA.getText().toString())+2;
                scoreA.setText(""+scA);
            }

        });
        Button btnA3 = (Button) findViewById(R.id.btnA3);
        btnA3.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                int scA = Integer.parseInt(scoreA.getText().toString())+1;
                scoreA.setText(""+scA);
            }

        });
        Button btnB1 = (Button) findViewById(R.id.btnB1);
        btnB1.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                int scB = Integer.parseInt(scoreB.getText().toString())+3;
                scoreB.setText(""+scB);
            }

        });
        Button btnB2 = (Button) findViewById(R.id.btnB2);
        btnB2.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                int scB = Integer.parseInt(scoreB.getText().toString())+2;
                scoreB.setText(""+scB);
            }

        });
        Button btnB3 = (Button) findViewById(R.id.btnB3);
        btnB3.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                int scB = Integer.parseInt(scoreB.getText().toString())+1;
                scoreB.setText(""+scB);
            }

        });

        Log.i(TAG, "onCreate: ");
    }
    @Override
    protected void onStart() {
        super.onStart();
        Log.i(TAG, "onStart: ");
    }
    @Override
    protected void onResume() {
        super.onResume();
        Log.i(TAG, "onResume: ");
    }
    @Override
    protected void onRestart() {
        super.onRestart();
        Log.i(TAG, "onRestart: ");
    }
    @Override
    protected void onPause() {
        super.onPause();
        Log.i(TAG, "onPause: ");
    }
    @Override
    protected void onStop() {
        super.onStop();
        Log.i(TAG, "onStop: ");
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.i(TAG, "onDestroy: ");
    }
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        String scorea = ((TextView)findViewById(R.id.scoreA)).getText().toString();
        String scoreb = ((TextView)findViewById(R.id.scoreB)).getText().toString();
        Log.i(TAG, "onSaveInstanceState: ");
        outState.putString("teama_score",scorea);
        outState.putString("teamb_score",scoreb);
    }
    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        String scorea = savedInstanceState.getString("teama_score");
        String scoreb = savedInstanceState.getString("teamb_score");
        Log.i(TAG, "onRestoreInstanceState: ");
        ((TextView)findViewById(R.id.scoreA)).setText(scorea);
        ((TextView)findViewById(R.id.scoreB)).setText(scoreb);
    }
}
