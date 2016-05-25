package popularmovies.one.fabianreddig.udacity.projectone.movieactivity;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
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
import popularmovies.one.fabianreddig.udacity.projectone.util.RxUtil;

/**
 * Created by Fabian Reddig on 5/23/16.
 */

public class MovieListFragment extends PaginatedFragment implements SwipeRefreshLayout.OnRefreshListener{
    private static final String TAG = MovieListFragment.class.getName();
    private static final String PAGINATE_ACTION = TAG + "_PAGINATE";

    @Inject
    TmdbApiWrapper apiWrapper;

    FragmentMovieListBinding fragmentMainListBinding;

    private boolean isRefreshing;
    private boolean isFirstRun;

    public static MovieListFragment newInstance(){
        return new MovieListFragment();
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
    }

    @Override
    public void onResume() {
        super.onResume();
        refresh(false); // TODO: 5/23/16 Handle resuming to a paginated state
    }

    private void loadMovies(int page){
        addSubscription(apiWrapper.getPopularMovieList(page).compose(RxUtil.singleBackgroundToMainThread())
                .subscribe(movieDbs -> {
                    getMovieListViewModel().addMovieDbs(movieDbs);
            isRefreshing = false;
            fragmentMainListBinding.swipeableRecyclerMain.refresh.setRefreshing(false);
        }));
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

    private void refresh(boolean swipeRefresh){
        getMovieListViewModel().clearList();
        if(!swipeRefresh){
            if(!isRefreshing){
                isRefreshing = true;
                paginate(page=0);
            }else{
                fragmentMainListBinding.swipeableRecyclerMain.refresh.setRefreshing(false);
            }
        }else{
            paginate(page=0);
        }
    }
}
