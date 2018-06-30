package com.bruce.ui.lsn6;

import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.SeekBar;

import com.bruce.ui.R;

public class RevealEffectActivity extends AppCompatActivity {

    private ImageView mRevealImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reveal_effect);
        mRevealImage = findViewById(R.id.iv_reveal_drawable_demo);
        RevealDrawable revealDrawable = new RevealDrawable(getResources().getDrawable(R.drawable.avft, null), getResources().getDrawable(R.drawable.avft_active, null));
        mRevealImage.setImageDrawable(revealDrawable);
        SeekBar seekBar = findViewById(R.id.sb_reveal_drawable_seek_bar);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            seekBar.setMin(0);
        }
        seekBar.setMax(10000);
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                Log.i("hzw", "progress = " + progress);
                mRevealImage.setImageLevel(progress);
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
