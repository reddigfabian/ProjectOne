package popularmovies.one.fabianreddig.udacity.projectone.common.activities;

import android.support.v7.app.AppCompatActivity;

/**
 * Created by Fabian Reddig on 05/23/2016.
 */
public class BaseActivity extends AppCompatActivity {
    @Override
    public void setTitle(CharSequence title) {
        if(getSupportActionBar()!=null)getSupportActionBar().setTitle(title);
    }

    @Override
    public void setTitle(int titleId) {
        if(getSupportActionBar()!=null)getSupportActionBar().setTitle(titleId);
    }
}
