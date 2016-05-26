package popularmovies.one.fabianreddig.udacity.projectone.movieactivity.viewmodels;

import java.util.List;

import javax.inject.Inject;

import info.movito.themoviedbapi.model.MovieDb;
import me.tatarka.bindingcollectionadapter.BR;
import popularmovies.one.fabianreddig.udacity.projectone.PopularMoviesApplication;
import popularmovies.one.fabianreddig.udacity.projectone.api.TmdbApiWrapper;
import popularmovies.one.fabianreddig.udacity.projectone.common.CustomItemViewSelector;
import popularmovies.one.fabianreddig.udacity.projectone.common.viewmodels.ListModel;
import popularmovies.one.fabianreddig.udacity.projectone.util.RxUtil;
import rx.Subscription;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by Fabian Reddig on 5/23/16.
 */

public class MovieListViewModel{

    @Inject
    TmdbApiWrapper apiWrapper;

    private CompositeSubscription subscription;
    private ListModel<MovieListItemViewModel> movies;

    private static MovieListViewModel instance;

    public void setOnLoadCompleteListener(OnLoadCompleteListener onLoadCompleteListener) {
        this.onLoadCompleteListener = onLoadCompleteListener;
    }

    public interface OnLoadCompleteListener{
        void onLoadComplete();
    }

    OnLoadCompleteListener onLoadCompleteListener;

    private MovieListViewModel(){
        PopularMoviesApplication.applicationComponent().inject(this);
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
        onLoadCompleteListener = null;
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

    public void loadMovies(int page){
        addSubscription(apiWrapper.getPopularMovieList(page).compose(RxUtil.singleBackgroundToMainThread())
                .subscribe(movieDbs -> {
                    addMovieDbs(movieDbs);// TODO: 5/25/16 Handle errors gracefully
                    onLoadCompleteListener.onLoadComplete();
                }));
    }
}
