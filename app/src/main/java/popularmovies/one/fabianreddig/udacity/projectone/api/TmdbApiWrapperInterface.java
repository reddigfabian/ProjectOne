package popularmovies.one.fabianreddig.udacity.projectone.api;

import java.util.List;

import info.movito.themoviedbapi.model.MovieDb;
import rx.Single;

/**
 * Created by Fabian Reddig on 05/23/2016.
 */
public interface TmdbApiWrapperInterface {

    Single<List<MovieDb>> getPopularMovieList(int page);

    Single<List<MovieDb>> getTopRatedMovieList(int page);

}
