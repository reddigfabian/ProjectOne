package popularmovies.one.fabianreddig.udacity.projectone.movieactivity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;

import javax.inject.Inject;

import popularmovies.one.fabianreddig.udacity.projectone.PopularMoviesApplication;
import popularmovies.one.fabianreddig.udacity.projectone.R;

public class MovieActivity extends AppCompatActivity {

    @Inject
    LocalBroadcastManager broadcastManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie);
        PopularMoviesApplication.applicationComponent().inject(this);
        if(savedInstanceState==null) {
            switchFragment(MovieListFragment.newInstance(),false);
        }
    }

    public void switchFragment(Fragment frag, boolean addToBackstack){
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction()
                                    .replace(R.id.main_fragment_holder, frag);
        if(addToBackstack){ft.addToBackStack(frag.getClass().getName());}
        ft.commit();
    }
}
