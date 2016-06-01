package popularmovies.one.fabianreddig.udacity.projectone.api;

import java.util.List;

import info.movito.themoviedbapi.TmdbApi;
import info.movito.themoviedbapi.TmdbMovies;
import info.movito.themoviedbapi.model.MovieDb;
import popularmovies.one.fabianreddig.udacity.projectone.BuildConfig;
import rx.Single;

/**
 * Created by Fabian Reddig on 05/23/2016.
 *
 * Wraps functionality of TmdbApi and adds some functionality
 */
public class TmdbApiWrapper implements TmdbApiWrapperInterface {

    public static final String IMAGE_BASE_URL = "http://image.tmdb.org/t/p/";
    public static final String IMAGE_WIDTH_PARAM_185 = "w185/";
    public static final String IMAGE_WIDTH_PARAM_342 = "w342/";

    public TmdbApi getApi() {
        return new TmdbApi(BuildConfig.THE_MOVIE_DB_API_KEY);
    }

    public TmdbMovies getMovies() {
        return getApi().getMovies();
    }

    @Override
    public Single<List<MovieDb>> getPopularMovieList(int page) {
        return Single.defer(() -> Single.just(getMovies().getPopularMovies("en", page).getResults()));
    }

    @Override
    public Single<List<MovieDb>> getTopRatedMovieList(int page) {
        return Single.defer(() -> Single.just(getMovies().getTopRatedMovies("en", page).getResults()));
    }
}
