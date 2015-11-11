package com.luigima.bmiviewapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.SeekBar;
import android.widget.TextView;

import com.luigima.bmiview.BMIView;

import butterknife.Bind;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    @Bind(R.id.seekBarHeight)
    SeekBar seekBarHeight;
    @Bind(R.id.seekBarWeight)
    SeekBar seekBarWeight;
    @Bind(R.id.textViewBMI)
    TextView textViewBMI;
    @Bind(R.id.textViewHeight)
    TextView textViewHeight;
    @Bind(R.id.textViewWeight)
    TextView textViewWeight;
    @Bind(R.id.bmiView)
    BMIView bmiView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        bmiView.setShowText(true)
                .setGender(0)
                .setWeight(80f);

        seekBarHeight.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                bmiView.setHeight(progress/100);
                textViewHeight.setText(progress / 100 + "m");
                textViewBMI.setText(String.valueOf(bmiView.getBmiValue()) + " " + bmiView.getBodyDescription());
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        seekBarWeight.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                bmiView.setWeight(progress);
                textViewWeight.setText(progress + "kg");
                textViewBMI.setText(String.valueOf(bmiView.getBmiValue()) + " " + bmiView.getBodyDescription());
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }
}
