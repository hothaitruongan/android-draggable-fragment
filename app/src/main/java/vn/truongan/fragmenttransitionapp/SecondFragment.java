package vn.truongan.fragmenttransitionapp;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.lang.ref.WeakReference;

/**
 * Author: truongan91
 * Created: 5/24/18
 * Project: FragmentTransitionApp
 */
public class SecondFragment
        extends Fragment
        implements CustomFragmentDragManager.OnFragmentDragListener {

    private ConstraintLayout vConstraintLayout;
    private static final String TAG = "SecondFragment";
    private CustomFragmentDragManager mFragmentDragManager;

    public static SecondFragment newInstance() {
        return new SecondFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_second, container, false);
    }

    private void initFragmentDragManager() {
        if (mFragmentDragManager == null) {
            mFragmentDragManager = new CustomFragmentDragManager(
                    new WeakReference<>((Activity) getActivity()),
                    this,
                    vConstraintLayout
            );
        }
    }

    private void bindView() {
        vConstraintLayout = getView().findViewById(R.id.constraintLayout);
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        bindView();
        initFragmentDragManager();
        initFragmentDragManager();
    }

    @Override
    public void onDragReleased(boolean willDismiss) {
        if (willDismiss) {
            ((MainActivity) getActivity()).pop();
        }
    }
}
