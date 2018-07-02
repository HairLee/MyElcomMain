package com.elcom.hailpt.util;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Build;
import android.transition.ChangeBounds;
import android.transition.Slide;
import android.transition.TransitionManager;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.Interpolator;

/**
 * Created by hoanv on 08/09/2015.
 */
public class AnimationUtil {
    @SuppressWarnings("unused")
    public static void flipTextView(final View view) {
        Interpolator accelerator = new AccelerateInterpolator();
        Interpolator decelerator = new DecelerateInterpolator();

        ObjectAnimator visToInvis = ObjectAnimator.ofFloat(view, "rotationY", 0f, 90f);
        visToInvis.setDuration(300);
        visToInvis.setInterpolator(accelerator);

        final ObjectAnimator invisToVis = ObjectAnimator.ofFloat(view, "rotationY",
                -90f, 0f);
        invisToVis.setDuration(300);
        invisToVis.setInterpolator(decelerator);

        visToInvis.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator anim) {
                view.setVisibility(View.GONE);
                invisToVis.start();
                view.setVisibility(View.VISIBLE);
            }
        });
        visToInvis.start();
    }

    public static void flipInvisToVis(View view) {
        Interpolator decelerator = new DecelerateInterpolator();

        final ObjectAnimator invisToVis = ObjectAnimator.ofFloat(view, "rotationY",
                -90f, 0f);
        invisToVis.setDuration(800);
        invisToVis.setInterpolator(decelerator);
        invisToVis.start();

        invisToVis.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animator) {

            }

            @Override
            public void onAnimationEnd(Animator animator) {
                if(mOnAnimationFinished != null){
                    mOnAnimationFinished.dofinish();
                }

            }

            @Override
            public void onAnimationCancel(Animator animator) {

            }

            @Override
            public void onAnimationRepeat(Animator animator) {

            }
        });
    }

    public static void flipTextView(View view, AnimatorListenerAdapter adapter) {
        Interpolator accelerator = new AccelerateInterpolator();

        ObjectAnimator visToInvis = ObjectAnimator.ofFloat(view, "rotationY", 0f, 90f);
        visToInvis.setDuration(300);
        visToInvis.setInterpolator(accelerator);
        visToInvis.addListener(adapter);
        visToInvis.start();
    }

    private static onAnimationFinished mOnAnimationFinished;

    public static void setAnimationfinished(onAnimationFinished pOnAnimationFinished){
        mOnAnimationFinished = pOnAnimationFinished;
    }

    public interface onAnimationFinished {
        void dofinish();
    }


    @SuppressLint("NewApi")
    public static void setAnimationSlideLeftWithTime(ViewGroup view, boolean isVisble){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                final ChangeBounds transition = new ChangeBounds();
                transition.setDuration(200L); // Sets a duration of 600 milliseconds
                TransitionManager.beginDelayedTransition(view, transition);

//                TransitionManager.beginDelayedTransition(view, new Slide(Gravity.LEFT));
                if (isVisble) {
                    view.setVisibility(View.VISIBLE);
                } else {
                    view.setVisibility(View.INVISIBLE);
                }
            }
        } else {
        }

    }

    @SuppressLint("NewApi")
    public static void setAnimationSlideLeftWithNoTime(ViewGroup view){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                TransitionManager.beginDelayedTransition(view);
            }
        }

    }

    @SuppressLint("NewApi")
    public static void setAnimationSlideLeft(ViewGroup view, boolean isVisble){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {

                TransitionManager.beginDelayedTransition(view, new Slide(Gravity.LEFT));
                if (isVisble) {
                    view.setVisibility(View.VISIBLE);
                } else {
                    view.setVisibility(View.INVISIBLE);
                }
            }
        } else {
        }

    }

    @SuppressLint("NewApi")
    public static void setAnimationSlideRight(ViewGroup view, boolean isVisble){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                TransitionManager.beginDelayedTransition(view, new Slide(Gravity.RIGHT));
                if (isVisble) {
                    view.setVisibility(View.VISIBLE);
                } else {
                    view.setVisibility(View.INVISIBLE);
                }
            }
        } else {
        }
    }

}
