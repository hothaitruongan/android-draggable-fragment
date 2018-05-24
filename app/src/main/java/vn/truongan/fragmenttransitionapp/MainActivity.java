package vn.truongan.fragmenttransitionapp;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.FrameLayout;

import java.util.Stack;

public class MainActivity
        extends AppCompatActivity {

    private Toolbar vToolbar;
    private FrameLayout vFrameLayout;
    private Stack<Fragment> mFragmentStack;
    private FragmentManager mFragmentManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
        loadInitialFragment();
    }

    private void setShowHomeIcon(boolean show) {
        getSupportActionBar().setDisplayHomeAsUpEnabled(show);
    }

    private void init() {
        setSupportActionBar((Toolbar) findViewById(R.id.toolbar));
        setShowHomeIcon(false);
        mFragmentStack = new Stack<>();
        mFragmentManager = getSupportFragmentManager();
    }

    private void loadInitialFragment() {
        push(FirstFragment.newInstance(), "1st", true);
    }

    public void showNextFragment() {
        push(SecondFragment.newInstance(), "2nd", false);
    }

    public void push(@NonNull Fragment f,
                     @NonNull String tag,
                     boolean isRoot) {
        FragmentTransaction ft = mFragmentManager.beginTransaction();
        if (isRoot) {
            ft.replace(R.id.frameLayout, f, tag);
        } else {

            ft.setCustomAnimations(R.anim.fragment_slide_in_right,
                                   0,
                                   0,
                                   0
            )
                    .add(R.id.frameLayout, f, tag);

            setShowHomeIcon(true);
        }
        mFragmentStack.push(f);
        ft.commit();
    }

    /**
     * remove last element in stack
     *
     * @return the last element after removed
     */
    @Nullable
    public Fragment pop() {
        if (!mFragmentStack.isEmpty() && mFragmentStack.size() > 1) {
            Fragment topFragment = mFragmentStack.pop();
            mFragmentManager.beginTransaction()
                    .setCustomAnimations(0,
                                         R.anim.fragment_slide_out_right,
                                         0,
                                         0
                    )
                    .remove(topFragment)
                    .commit();
        } else {
        }
        setShowHomeIcon(false);
        return getLastElement();
    }

    @Nullable
    private Fragment getLastElement() {
        if (mFragmentStack.isEmpty()) {
            return null;
        }
        return mFragmentStack.peek();
    }

    private boolean isLastElement() {
        return mFragmentStack.size() == 1;
    }

    @Override
    public void onBackPressed() {
        if (!isLastElement()) {
            pop();
            return;
        }
        super.onBackPressed();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }
}
