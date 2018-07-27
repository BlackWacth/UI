package com.bruce.ui.lsn12;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.os.Handler;
import android.print.PrinterId;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewPropertyAnimator;
import android.widget.Button;

import com.bruce.ui.BaseActivity;
import com.bruce.ui.R;

public class Lsn12HomeActivity extends BaseActivity {

    private static final Handler mHandler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lsn12_home);

        final Button normalButton = findViewById(R.id.btn_normal_animator);
        final Button rtButton = findViewById(R.id.btn_rt_animator);

        normalButton.setScaleY(1f);
        rtButton.setScaleY(1f);

        normalButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startAnim1(normalButton);
                startAnim2(rtButton);
                delaySleep(1000, 1000);
            }
        });
    }

    private void startAnim1(View v) {
        ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(v, "scaleY", 1, 2);
        objectAnimator.setDuration(2000);
        objectAnimator.start();
    }

    private void startAnim2(View v) {
        v.setScaleY(1);
        ViewPropertyAnimator animator = v.animate().scaleY(2).setDuration(2000);
        AnimatorRTHelper.setAnimatorByRT(animator, v);
        animator.setListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                Log.i("hzw", "AnimatorRTHelper --> onAnimationEnd");
            }
        });
        animator.start();
    }

    private void delaySleep(long delay, final long duration) {
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(duration);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }, delay);
    }
}

