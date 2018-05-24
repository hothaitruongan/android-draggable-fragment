package vn.truongan.fragmenttransitionapp;

import android.app.Activity;
import android.support.v4.app.FragmentActivity;

import java.lang.ref.WeakReference;

/**
 * Author: truongan91
 * Created: 5/24/18
 * Project: FragmentTransitionApp
 */
public class Utils {

    public static final float getCenterX(WeakReference<Activity> weakReference) {
        Activity activity = weakReference.get();
        return activity.getResources().getDisplayMetrics().widthPixels / 2;
    }
}
