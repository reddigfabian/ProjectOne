package popularmovies.one.fabianreddig.udacity.projectone.movieactivity.viewmodels;

import android.support.annotation.IntDef;
import android.util.Log;

import java.util.List;

import javax.inject.Inject;

import info.movito.themoviedbapi.model.MovieDb;
import me.tatarka.bindingcollectionadapter.BR;
import popularmovies.one.fabianreddig.udacity.projectone.PopularMoviesApplication;
import popularmovies.one.fabianreddig.udacity.projectone.api.TmdbApiWrapper;
import popularmovies.one.fabianreddig.udacity.projectone.common.CustomItemViewSelector;
import popularmovies.one.fabianreddig.udacity.projectone.common.viewmodels.ListModel;
import popularmovies.one.fabianreddig.udacity.projectone.util.RxUtil;
import popularmovies.one.fabianreddig.udacity.projectone.util.UiUtil;
import rx.Single;
import rx.Subscriber;
import rx.Subscription;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by Fabian Reddig on 5/23/16.
 */

public class MovieListViewModel{
    private static final String TAG = MovieListViewModel.class.getName();

    public static final int POPULAR = 0, TOP_RATED = 1;
    private int currentSortOrder;

    @Inject
    TmdbApiWrapper apiWrapper;

    private CompositeSubscription subscription;
    private ListModel<MovieListItemViewModel> movies;
    private int listPosition;

    private static MovieListViewModel instance;

    public void setOnLoadCompleteListener(OnLoadCompleteListener onLoadCompleteListener) {
        this.onLoadCompleteListener = onLoadCompleteListener;
    }

    public interface OnLoadCompleteListener{
        void onLoadComplete();
        void onLoadError(Throwable e);
    }

    private OnLoadCompleteListener onLoadCompleteListener;

    private MovieListViewModel(){
        PopularMoviesApplication.applicationComponent().inject(this);
        currentSortOrder = POPULAR;
        instance = this;
        movies = new ListModel<>(new CustomItemViewSelector<>(BR.viewModel));
    }

    public int getCurrentSortOrder() {
        return currentSortOrder;
    }

    public void setCurrentSortOrder(@SortOrder int sortOrder) {
        this.currentSortOrder = sortOrder;
    }

    public ListModel<MovieListItemViewModel> getMovies() {
        return movies;
    }

    private void clearList(){
        movies.clearModels();
    }

    public static MovieListViewModel getInstance(){
        if(instance == null) {
            instance = new MovieListViewModel();
        }
        return instance;
    }

    private void addMovieDbs(List<MovieDb> movieDbs) {
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
            subscription = null;
        }
        onLoadCompleteListener = null;
    }

    private void addSubscription(Subscription subscription) {
        this.subscription.add(subscription);
    }

    private void clearSubscriptions() {
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
        addSubscription(getMovieListSingleBySortOrder(page)
                .subscribe(new Subscriber<List<MovieDb>>() {
                    @Override
                    public void onCompleted() {
                        onLoadCompleteListener.onLoadComplete();
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e(TAG, e.getMessage());
                        onLoadCompleteListener.onLoadError(e);
                    }

                    @Override
                    public void onNext(List<MovieDb> movieDbs) {
                        addMovieDbs(movieDbs);
                    }
                }));
    }

    private Single<List<MovieDb>> getMovieListSingleBySortOrder(int page) {
        if(currentSortOrder == TOP_RATED) {
            return apiWrapper.getTopRatedMovieList(page).compose(RxUtil.singleBackgroundToMainThread());
        }else{
            return apiWrapper.getPopularMovieList(page).compose(RxUtil.singleBackgroundToMainThread());
        }
    }

    public int columnCount(){
        return UiUtil.getListColumnCount();
    }

    public int getListPosition() {
        return listPosition;
    }

    public void setListPosition(int listPosition) {
        this.listPosition = listPosition;
    }

    @IntDef({POPULAR, TOP_RATED})
    public @interface SortOrder{}
}
