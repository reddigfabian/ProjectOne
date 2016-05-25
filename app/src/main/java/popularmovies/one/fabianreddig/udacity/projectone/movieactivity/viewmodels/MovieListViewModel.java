package popularmovies.one.fabianreddig.udacity.projectone.movieactivity.viewmodels;

import java.util.List;

import info.movito.themoviedbapi.model.MovieDb;
import me.tatarka.bindingcollectionadapter.BR;
import popularmovies.one.fabianreddig.udacity.projectone.common.CustomItemViewSelector;
import popularmovies.one.fabianreddig.udacity.projectone.common.viewmodels.ListModel;

/**
 * Created by Fabian Reddig on 5/23/16.
 */

public class MovieListViewModel{
    private ListModel<MovieListItemViewModel> movies;

    private static MovieListViewModel instance;

    private MovieListViewModel(){
        instance = this;
        movies = new ListModel<>(new CustomItemViewSelector<>(BR.viewModel));
    }

    public ListModel<MovieListItemViewModel> getMovies() {
        return movies;
    }

    public void clearList(){
        movies.clearModels();
    }

    public static MovieListViewModel getInstance(){
        if(instance == null) {
            instance = new MovieListViewModel();
        }
        return instance;
    }

    public void addMovieDbs(List<MovieDb> movieDbs) {
        for (MovieDb movieDb : movieDbs) {
            movies.addModel(new MovieListItemViewModel(movieDb));
        }
    }
}
