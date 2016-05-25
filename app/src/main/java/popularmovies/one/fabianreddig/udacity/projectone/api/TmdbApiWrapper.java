package popularmovies.one.fabianreddig.udacity.projectone.api;

import java.util.List;

import info.movito.themoviedbapi.TmdbApi;
import info.movito.themoviedbapi.TmdbMovies;
import info.movito.themoviedbapi.model.MovieDb;
import popularmovies.one.fabianreddig.udacity.projectone.BuildConfig;
import rx.Single;

/**
 * Created by Fabian Reddig on 05/23/2016.
 */
public class TmdbApiWrapper implements TmdbApiWrapperInterface {
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
