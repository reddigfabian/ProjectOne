package popularmovies.one.fabianreddig.udacity.projectone.util;

import android.content.Context;
import android.util.TypedValue;

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

    public static int dpToPx(Context context, int dp) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, context.getResources().getDisplayMetrics());
    }

    public static int getListPosterWidth(){
        return ((int) PopularMoviesApplication.getApp().getResources().getDimension(R.dimen.movie_list_poster_width));
    }

    public static int getListPosterHeight(){
        return ((int) PopularMoviesApplication.getApp().getResources().getDimension(R.dimen.movie_list_poster_height));
    }
}
