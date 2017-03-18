package com.kongsin.kognsin.animate;

import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.kongsin.kanimationcontroller.AnimationQueue;
import com.kongsin.kanimationcontroller.BaseAnimationControl;
import com.kongsin.kanimationcontroller.ImageAnimationControl;

import java.util.Random;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, AnimationQueue.AnimatedCallback {
    RelativeLayout main;
    ImageView img, img2, img3, play;

    private static final String TAG = "MainActivity";
    private Random mRan;
    private int mCount = 0;
    private int mLastRes = 0;
    private int maxCount = 10;

    public void init(){
        setContentView(R.layout.activity_main);
        main = (RelativeLayout) findViewById(R.id.main);
        play = (ImageView) findViewById(R.id.paly);
        img = (ImageView) findViewById(R.id.card1);
        img2 = (ImageView) findViewById(R.id.card2);
        img3 = (ImageView) findViewById(R.id.card3);
        img.setOnClickListener(this);
        img2.setOnClickListener(this);
        img3.setOnClickListener(this);
        play.setOnClickListener(this);
        mRan = new Random();
        firstSetupView();
    }

    @Override
    public void onClick(final View v) {

    }

    private int getRandomMax() {
        return mRan.nextInt(10 - 5) + 5;
    }



    public void firstSetupView(){
        main.post(new Runnable() {
            @Override
            public void run() {
                AnimationQueue animationQueue = new AnimationQueue(0, new ImageAnimationControl(img).goToLeft(main).moveToCenterVertical(main).marginLeft(50));
                animationQueue.nextQueue(0, new ImageAnimationControl(img2).moveToCenterVertical(main).moveToCenterHorizontal(main));
                animationQueue.nextQueue(0, new ImageAnimationControl(img3).goToRight(main).moveToCenterVertical(main).marginRight(50));
                animationQueue.setCallback(new AnimationQueue.AnimatedCallback() {
                    @Override
                    public void finished() {

                    }

                    @Override
                    public void eachQueueFinished(BaseAnimationControl control) {
                        Log.i(TAG, "eachQueueFinished: " + control.getView().getId());
                    }
                });
                animationQueue.startTogether();
            }
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init();
    }

    @Override
    public void finished() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

            }
        }, 120);
    }

    @Override
    public void eachQueueFinished(BaseAnimationControl control) {

    }
}
