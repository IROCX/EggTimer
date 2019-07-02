package com.example.eggtimer;

import androidx.appcompat.app.AppCompatActivity;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    Integer time;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final CheckBox checkBox = findViewById(R.id.checkBox);

        final Button button = findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final EditText editText = findViewById(R.id.editText);
                String buttonText = button.getText().toString();
                final TextView textView = findViewById(R.id.textView);
                SeekBar seekBar = findViewById(R.id.seekBar);

                if (buttonText.matches("start")) {
                    if(editText.getText().toString().matches("")){
                        Toast.makeText(getApplicationContext(),"Please enter the timer duration.",Toast.LENGTH_SHORT).show();
                    }
                    else {
                        time = Integer.parseInt(editText.getText().toString());
                        editText.onEditorAction(EditorInfo.IME_ACTION_DONE);

                        seekBar.setMax(time);
                        seekBar.setProgress(time);
                        seekBar.animate().alpha(1f).setDuration(100);

                        button.animate().alpha(0f).setDuration(100);
                        editText.animate().alpha(0f).setDuration(100);
                        textView.animate().alpha(1f).setDuration(100);
                        textView.setText(time + "");

                        new CountDownTimer(time * 1000, 1000) {
                            SeekBar seekBar = findViewById(R.id.seekBar);
                            TextView textView = findViewById(R.id.textView);

                            @Override
                            public void onTick(long l) {
                                seekBar.setProgress((int)(l/1000));
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
                    editText.animate().alpha(1f).setDuration(100);
                    textView.animate().alpha(0f).setDuration(100);
                }

            }
        });

    }
}
