package popularmovies.one.fabianreddig.udacity.projectone.movieactivity.viewmodels;

import android.content.Intent;
import android.support.v4.content.LocalBroadcastManager;
import android.view.View;

import javax.inject.Inject;

import info.movito.themoviedbapi.model.MovieDb;
import popularmovies.one.fabianreddig.udacity.projectone.PopularMoviesApplication;
import popularmovies.one.fabianreddig.udacity.projectone.R;
import popularmovies.one.fabianreddig.udacity.projectone.common.viewmodels.BaseViewModel;

/**
 * Created by Fabian Reddig on 5/23/16.
 */
public class MovieListItemViewModel extends BaseViewModel{
    public static final String ON_MOVIE_CLICKED = "on_movie_clicked";

    @Inject
    LocalBroadcastManager broadcastManager;

    private MovieDb movie;

    public MovieListItemViewModel(MovieDb movie){
        PopularMoviesApplication.applicationComponent().inject(this);
        this.movie = movie;
    }

    public void onMovieClicked(View v){
        Intent petClickIntent = new Intent(ON_MOVIE_CLICKED);
        broadcastManager.sendBroadcast(petClickIntent);
    }

    public String movieText(){
        return movie.getTitle();
    }

    @Override
    public int getLayoutID() {
        return R.layout.view_main_list_movie_item;
    }
}
