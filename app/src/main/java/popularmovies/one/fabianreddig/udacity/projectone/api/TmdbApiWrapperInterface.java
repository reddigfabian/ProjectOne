package popularmovies.one.fabianreddig.udacity.projectone.api;

import java.util.List;

import info.movito.themoviedbapi.model.MovieDb;
import rx.Single;

/**
 * Created by WillowTree, Inc. on 5/24/16.
 */
public interface TmdbApiWrapperInterface {

    Single<List<MovieDb>> getPopularMovieList(int page);

    Single<List<MovieDb>> getTopRatedMovieList(int page);

}
