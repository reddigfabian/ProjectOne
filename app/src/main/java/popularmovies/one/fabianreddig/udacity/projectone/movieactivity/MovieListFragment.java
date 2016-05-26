package popularmovies.one.fabianreddig.udacity.projectone.movieactivity;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import javax.inject.Inject;

import popularmovies.one.fabianreddig.udacity.projectone.PopularMoviesApplication;
import popularmovies.one.fabianreddig.udacity.projectone.R;
import popularmovies.one.fabianreddig.udacity.projectone.api.TmdbApiWrapper;
import popularmovies.one.fabianreddig.udacity.projectone.common.adapters.ListPaginatedAdapter;
import popularmovies.one.fabianreddig.udacity.projectone.common.fragments.PaginatedFragment;
import popularmovies.one.fabianreddig.udacity.projectone.databinding.FragmentMovieListBinding;
import popularmovies.one.fabianreddig.udacity.projectone.movieactivity.viewmodels.MovieListViewModel;

/**
 * Created by Fabian Reddig on 5/23/16.
 */

public class MovieListFragment extends PaginatedFragment implements SwipeRefreshLayout.OnRefreshListener, MovieListViewModel.OnLoadCompleteListener{
    private static final String TAG = MovieListFragment.class.getName();
    private static final String PAGINATE_ACTION = TAG + "_PAGINATE";

    @Inject
    TmdbApiWrapper apiWrapper;

    FragmentMovieListBinding fragmentMainListBinding;

    private boolean isRefreshing;

    public static MovieListFragment newInstance(){
        return new MovieListFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getMovieListViewModel().attachToView();
        getMovieListViewModel().setOnLoadCompleteListener(this);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        PopularMoviesApplication.applicationComponent().inject(this);
        databindingSetup(container);
        return fragmentMainListBinding.getRoot();
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recyclerSetup();
        if(savedInstanceState==null){
            refresh(false);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        getMovieListViewModel().detachFromView();
    }

    private void loadMovies(int page){
        getMovieListViewModel().loadMovies(page);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_movie_list;
    }

    @Override
    public String getPaginationAction() {
        return PAGINATE_ACTION;
    }

    @Override
    public void paginate(int page) {
        loadMovies(page);
    }

    @Override
    public void update() {
        if (fragmentMainListBinding != null && fragmentMainListBinding.swipeableRecyclerMain.recycler.getAdapter() != null) {
            fragmentMainListBinding.swipeableRecyclerMain.recycler.getAdapter().notifyDataSetChanged();
        }
    }

    private void databindingSetup(ViewGroup container){
        fragmentMainListBinding = DataBindingUtil.inflate(LayoutInflater.from(getActivity()), getLayoutId(), container, false);
        fragmentMainListBinding.setMovieListViewModel(getMovieListViewModel());
        fragmentMainListBinding.executePendingBindings();
    }

    private void recyclerSetup(){
        fragmentMainListBinding.swipeableRecyclerMain.refresh.setOnRefreshListener(this);
        ((ListPaginatedAdapter) fragmentMainListBinding.swipeableRecyclerMain.recycler.getAdapter()).setPaginateAction(getPaginationAction());
    }

    private MovieListViewModel getMovieListViewModel(){
        return MovieListViewModel.getInstance();
    }

    @Override
    public void onRefresh() {
        refresh(true);
    }

    @Override
    public void onLoadComplete() {
        stopRefreshing();
    }

    @Override
    public void onLoadError(Throwable e) {
        Log.e(TAG, e.getMessage());
        //TODO Handle this in the UI
    }

    private void refresh(boolean swipeRefresh){
        if(!swipeRefresh){
            if(!isRefreshing){
                getMovieListViewModel().refresh();
                startRefreshing();
                paginate(page=1);
            }else{
                fragmentMainListBinding.swipeableRecyclerMain.refresh.setRefreshing(false);
            }
        }else{
            getMovieListViewModel().refresh();
            paginate(page=1);
        }
    }

    private void startRefreshing(){
        isRefreshing = true;
        fragmentMainListBinding.swipeableRecyclerMain.swipeableRecyclerMainProgress.setVisibility(View.VISIBLE);
    }

    private void stopRefreshing(){
        isRefreshing = false;
        fragmentMainListBinding.swipeableRecyclerMain.swipeableRecyclerMainProgress.setVisibility(View.GONE);
        fragmentMainListBinding.swipeableRecyclerMain.refresh.setRefreshing(false);
    }
}
