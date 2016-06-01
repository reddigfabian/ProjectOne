package popularmovies.one.fabianreddig.udacity.projectone.util;

import popularmovies.one.fabianreddig.udacity.projectone.PopularMoviesApplication;
import popularmovies.one.fabianreddig.udacity.projectone.R;

/**
 * Created by WillowTree, Inc. on 4/3/16.
 */
public class UiUtil {

    //Suppress default constructor for noninstantiability
    private UiUtil(){
        throw new AssertionError();
    }

    public static int getListPosterWidth(){
        return ((int) PopularMoviesApplication.getApp().getResources().getDimension(R.dimen.movie_list_poster_width));
    }

    public static int getListPosterHeight(){
        return ((int) PopularMoviesApplication.getApp().getResources().getDimension(R.dimen.movie_list_poster_height));
    }

    public static int getDetailPosterWidth(){
        return ((int) PopularMoviesApplication.getApp().getResources().getDimension(R.dimen.movie_detail_poster_width));
    }

    public static int getDetailPosterHeight(){
        return ((int) PopularMoviesApplication.getApp().getResources().getDimension(R.dimen.movie_detail_poster_height));
    }

    public static int getListColumnCount(){
        return ((int) PopularMoviesApplication.getApp().getResources().getInteger(R.integer.movie_list_column_count));
    }
}
