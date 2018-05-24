package vn.truongan.fragmenttransitionapp;

import android.animation.Animator;
import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;

import java.lang.ref.WeakReference;

/**
 * Author: truongan91
 * Created: 5/24/18
 * Project: FragmentTransitionApp
 */
public class CustomFragmentDragManager
        implements View.OnTouchListener {

    private View.OnTouchListener mOnTouchListener;
    private OnFragmentDragListener mOnFragmentDragListener;
    private WeakReference<Activity> mWeakReference;
    private View mContainerView;
    private boolean mIsDragging;
    private float mMoveFromX, mMoveToX;


    public CustomFragmentDragManager(@NonNull WeakReference<Activity> weakReference,
                                     @Nullable OnFragmentDragListener mOnFragmentDragListener,
                                     @NonNull View mContainerView) {
        this.mWeakReference = weakReference;
        this.mOnFragmentDragListener = mOnFragmentDragListener;
        this.mContainerView = mContainerView;
        attachTouchListener();
    }

    private void attachTouchListener() {
        if(mOnFragmentDragListener != null) {
            this.mContainerView.setOnTouchListener(this);
        }
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                mMoveFromX = event.getRawX();
                mMoveToX = 0;
                float downY = event.getRawY();
                return true;
            case MotionEvent.ACTION_UP:
                if (mIsDragging) {
                    float centerScreenX = Utils.getCenterX(mWeakReference);
                    if ((mMoveToX - mMoveFromX) > centerScreenX) {
                        // dismiss this fragment
                        v.animate().translationX((centerScreenX * 2))
                                .setDuration(80)
                                .setInterpolator(new AccelerateDecelerateInterpolator())
                                .setListener(new Animator.AnimatorListener() {
                                    @Override
                                    public void onAnimationStart(Animator animation) {

                                    }

                                    @Override
                                    public void onAnimationEnd(Animator animation) {
                                        // ((MainActivity) mWeakReference.get()).pop();
                                        mOnFragmentDragListener.onDragReleased(true);
                                    }

                                    @Override
                                    public void onAnimationCancel(Animator animation) {

                                    }

                                    @Override
                                    public void onAnimationRepeat(Animator animation) {

                                    }
                                })
                                .start();
                    } else {
                        // revert to cancel
                        v.animate().translationX(0)
                                .setDuration(80)
                                .setInterpolator(new AccelerateDecelerateInterpolator())
                                .start();
                    }
                    mMoveFromX = 0;
                    mMoveToX = 0;
                    mIsDragging = false;
                    if(mOnFragmentDragListener != null) {
                        mOnFragmentDragListener.onDragReleased(false);
                    }
                }
                return true;
            case MotionEvent.ACTION_MOVE:
                mIsDragging = true;
                mMoveToX = event.getRawX();
                float moveY = event.getRawY();
                v.animate().translationX(mMoveToX - mMoveFromX)
                        .setDuration(0)
                        .start();
                return true;
        }
        return false;
    }


    public interface OnFragmentDragListener {

        void onDragReleased(boolean willDismiss);
    }
}
