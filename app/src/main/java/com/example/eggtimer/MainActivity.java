package com.example.eggtimer;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    Integer time;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final CheckBox checkBox = findViewById(R.id.checkBox);
        final TextView textView = findViewById(R.id.textView);
        final Button button = findViewById(R.id.button);
        final EditText editText = findViewById(R.id.editText);

        textView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                return true;
            }
        });

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                String buttonText = button.getText().toString();

                SeekBar seekBar = findViewById(R.id.seekBar);

                if (buttonText.matches("start")) {
                    if(editText.getText().toString().matches("")){
                        Toast.makeText(getApplicationContext(),"Please enter the timer duration.",Toast.LENGTH_SHORT).show();
                    }
                    else {
                        time = Integer.parseInt(editText.getText().toString());
                        editText.onEditorAction(EditorInfo.IME_ACTION_DONE);

                        seekBar.setMax(time * 1000);
                        seekBar.setProgress(time * 1000);
                        seekBar.animate().alpha(1f).setDuration(100);

                        button.animate().alpha(0f).setDuration(100);
                        editText.animate().alpha(0f).setDuration(100);
                        textView.animate().alpha(1f).setDuration(100);
                        textView.setText(time + "");
                        textView.bringToFront();

                        new CountDownTimer(time * 1000, 1) {
                            SeekBar seekBar = findViewById(R.id.seekBar);
                            TextView textView = findViewById(R.id.textView);

                            @Override
                            public void onTick(long l) {
                                seekBar.setProgress((int) (l));
                                textView.setText(String.format("%d", (int) l/1000));
                                Log.i("TAG", "started");
                            }

                            @Override
                            public void onFinish() {
                                seekBar.setProgress(0);
                                textView.setText("Countdown ended.");
                                button.setText("reset");
                                button.animate().alpha(1f).setDuration(100);
                                if(checkBox.isChecked()) {
                                    final MediaPlayer mediaPlayer;
                                    mediaPlayer = MediaPlayer.create(getApplicationContext(), R.raw.airhorn);
                                    mediaPlayer.start();
                                }


                            }
                        }.start();
                    }
                } else if (buttonText.matches("reset")) {
                    seekBar.animate().alpha(0f).setDuration(100);
                    button.setText("start");
                    editText.setText("");
                    editText.bringToFront();
                    editText.animate().alpha(1f).setDuration(100);
                    textView.animate().alpha(0f).setDuration(100);
                }

            }
        });

    }
}
