package com.luigima.bmiviewapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.CompoundButton;
import android.widget.SeekBar;
import android.widget.Switch;
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
    @Bind(R.id.switchGender)
    Switch switchGender;
    @Bind(R.id.textViewGender)
    TextView textViewGender;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        bmiView.setGender(0)
                .setWeight(0)
        .setHeight(0);

        seekBarHeight.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                invalide();
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
                invalide();
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        switchGender.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked) {
                    bmiView.setGender(1);
                    textViewGender.setText("Female");
                } else {
                    bmiView.setGender(0);
                    textViewGender.setText("Male");
                }
                invalide();
            }
        });

    }
    public void invalide() {
        textViewBMI.setText(String.valueOf(bmiView.getBmiValue()) + " " + bmiView.getBodyDescription());
        bmiView.setHeight(seekBarHeight.getProgress() / 100f);
        textViewHeight.setText(seekBarHeight.getProgress() / 100f + "m");
        bmiView.setWeight(seekBarWeight.getProgress());
        textViewWeight.setText(seekBarWeight.getProgress() + "kg");
    }
}
