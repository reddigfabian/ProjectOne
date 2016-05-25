package popularmovies.one.fabianreddig.udacity.projectone.movieactivity.viewmodels;

import java.util.List;

import info.movito.themoviedbapi.model.MovieDb;
import me.tatarka.bindingcollectionadapter.BR;
import popularmovies.one.fabianreddig.udacity.projectone.common.CustomItemViewSelector;
import popularmovies.one.fabianreddig.udacity.projectone.common.viewmodels.ListModel;
import rx.Subscription;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by Fabian Reddig on 5/23/16.
 */

public class MovieListViewModel{
    private CompositeSubscription subscription;
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

    public final void attachToView(){
        subscription = new CompositeSubscription();
    }

    public final void detachFromView(){
        if(!subscription.isUnsubscribed()){
            subscription.unsubscribe();
        }
    }

    public void addSubscription(Subscription subscription) {
        this.subscription.add(subscription);
    }

    public void clearSubscriptions() {
        if (!subscription.isUnsubscribed()) {
            subscription.unsubscribe();
        }
        subscription = new CompositeSubscription();
    }

    public void refresh(){
        clearList();
        clearSubscriptions();
    }
}
