package popularmovies.one.fabianreddig.udacity.projectone.movieactivity.viewmodels;

import android.content.Intent;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.v4.content.LocalBroadcastManager;
import android.view.View;

import javax.inject.Inject;

import info.movito.themoviedbapi.model.MovieDb;
import popularmovies.one.fabianreddig.udacity.projectone.PopularMoviesApplication;
import popularmovies.one.fabianreddig.udacity.projectone.R;
import popularmovies.one.fabianreddig.udacity.projectone.api.TmdbApiWrapper;
import popularmovies.one.fabianreddig.udacity.projectone.common.viewmodels.BaseViewModel;

/**
 * Created by Fabian Reddig on 5/23/16.
 */
public class MovieListItemViewModel extends BaseViewModel implements Parcelable{
    public static final String ON_MOVIE_CLICKED = "on_movie_clicked";
    public static final String CLICKED_MOVIE = "clicked_movie";

    @Inject
    LocalBroadcastManager broadcastManager;

    private MovieDb movie;

    public MovieListItemViewModel(MovieDb movie){
        PopularMoviesApplication.applicationComponent().inject(this);
        this.movie = movie;
    }

    protected MovieListItemViewModel(Parcel in) {
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<MovieListItemViewModel> CREATOR = new Creator<MovieListItemViewModel>() {
        @Override
        public MovieListItemViewModel createFromParcel(Parcel in) {
            return new MovieListItemViewModel(in);
        }

        @Override
        public MovieListItemViewModel[] newArray(int size) {
            return new MovieListItemViewModel[size];
        }
    };

    public void onMovieClicked(View v){
        Intent movieClickIntent = new Intent(ON_MOVIE_CLICKED);
        movieClickIntent.putExtra(CLICKED_MOVIE, this);
        broadcastManager.sendBroadcast(movieClickIntent);
    }

    public String getTitle(){
        return movie.getTitle();
    }

    public String completeSmallPosterPath(){
        return TmdbApiWrapper.IMAGE_BASE_URL + TmdbApiWrapper.IMAGE_WIDTH_PARAM_185 + movie.getPosterPath();
    }

    public String completeMediumPosterPath(){
        return TmdbApiWrapper.IMAGE_BASE_URL + TmdbApiWrapper.IMAGE_WIDTH_PARAM_342 + movie.getPosterPath();
    }

    public String getMovieOverview(){
        return movie.getOverview();
    }

    @Override
    public int getLayoutID() {
        return R.layout.view_main_list_movie_item;
    }
}
