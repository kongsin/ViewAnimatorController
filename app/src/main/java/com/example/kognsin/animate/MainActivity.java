package com.example.kognsin.animate;

import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.example.kanimationcontroller.AnimationQueue;
import com.example.kanimationcontroller.BaseAnimationControl;
import com.example.kanimationcontroller.ImageAnimationControl;

import java.util.Random;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, AnimationQueue.AnimatedCallback {
    LinearLayout main;
    ImageView img, img2, img3, play;

    private static final String TAG = "MainActivity";
    private Random mRan;
    private int mCount = 0;
    private int mLastRes = 0;

    public void init(){
        setContentView(R.layout.activity_main);
        main = (LinearLayout) findViewById(R.id.main);
        play = (ImageView) findViewById(R.id.paly);
        img = (ImageView) findViewById(R.id.card1);
        img2 = (ImageView) findViewById(R.id.card2);
        img3 = (ImageView) findViewById(R.id.card3);
        img.setOnClickListener(this);
        img2.setOnClickListener(this);
        img3.setOnClickListener(this);
        play.setOnClickListener(this);
        mRan = new Random();
    }

    @Override
    public void onClick(final View v) {
        if (v == play){
            mCount = 0;
            restoreFlip();
        } else {
            flip((ImageView) v);
        }
    }

    private void randomCard() {
        if (mCount > 10) return;
        while (true) {
            int res = mRan.nextInt(3) + 1;
            if (res != mLastRes) {
                mLastRes = res;
                Log.i(TAG, "randomCard: " + res);
                switch (res) {
                    case 1:
                        randDom1();
                        break;
                    case 2:
                        randDom2();
                        break;
                    case 3:
                        randDom3();
                        break;
                }
                mCount ++;
                break;
            }
        }
    }

    public void flip(final ImageView view){
        AnimationQueue animationQueue = new AnimationQueue(0, new ImageAnimationControl(view).goToTop(main).start());
        animationQueue.nextQueue(0, new ImageAnimationControl(view).flip(-180).setDuration(200));
        animationQueue.setCallback(new AnimationQueue.AnimatedCallback() {
            @Override
            public void finished() {
                if (view == img2) {
                    view.setImageResource(R.mipmap.ic_launcher);
                }
            }

            @Override
            public void eachQueueFinishe(BaseAnimationControl control) {

            }
        });
        animationQueue.start();
    }

    private void restoreFlip() {
        AnimationQueue animationQueue = new AnimationQueue(0, new ImageAnimationControl(img).flip(0).setDuration(80));
        animationQueue.nextQueue(0, new ImageAnimationControl(img2).flip(0).setDuration(80));
        animationQueue.nextQueue(0, new ImageAnimationControl(img3).flip(0).setDuration(80));
        animationQueue.setCallback(new AnimationQueue.AnimatedCallback() {
            @Override
            public void finished() {
                randomCard();
            }

            @Override
            public void eachQueueFinishe(BaseAnimationControl control) {
                if (control instanceof ImageAnimationControl) {
                    ((ImageView) control.getView()).setImageBitmap(null);
                }
            }
        });
        animationQueue.start();
    }

    private void randDom1(){
        AnimationQueue animationQueue = new AnimationQueue(0, new ImageAnimationControl(img).goToTop(main).moveToCenterHorizontal(main).setDuration(50));
        animationQueue.nextQueue(0,new ImageAnimationControl(img2).goToLeft(main).setDuration(50));
        animationQueue.nextQueue(0,new ImageAnimationControl(img3).goToRight(main).setDuration(50));
        animationQueue.nextQueue(0, new ImageAnimationControl(img).moveToCenterVertical(main).setDuration(50));
        animationQueue.setCallback(this);
        animationQueue.start();
    }

    private void randDom2(){
        AnimationQueue animationQueue = new AnimationQueue(0, new ImageAnimationControl(img2).goToTop(main).moveToCenterHorizontal(main).setDuration(50));
        animationQueue.nextQueue(0,new ImageAnimationControl(img3).goToLeft(main).setDuration(50));
        animationQueue.nextQueue(0,new ImageAnimationControl(img).goToRight(main).setDuration(50));
        animationQueue.nextQueue(0, new ImageAnimationControl(img2).moveToCenterVertical(main).setDuration(50));
        animationQueue.setCallback(this);
        animationQueue.start();
    }

    private void randDom3(){
        AnimationQueue animationQueue = new AnimationQueue(0, new ImageAnimationControl(img3).goToTop(main).moveToCenterHorizontal(main).setDuration(50));
        animationQueue.nextQueue(0,new ImageAnimationControl(img).goToLeft(main).setDuration(50));
        animationQueue.nextQueue(0,new ImageAnimationControl(img2).goToRight(main).setDuration(50));
        animationQueue.nextQueue(0, new ImageAnimationControl(img3).moveToCenterVertical(main).setDuration(50));
        animationQueue.setCallback(this);
        animationQueue.start();
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
                randomCard();
            }
        }, 120);
    }

    @Override
    public void eachQueueFinishe(BaseAnimationControl control) {

    }
}

