package popularmovies.one.fabianreddig.udacity.projectone.movieactivity;

import android.annotation.TargetApi;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.databinding.DataBindingUtil;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import javax.inject.Inject;

import popularmovies.one.fabianreddig.udacity.projectone.PopularMoviesApplication;
import popularmovies.one.fabianreddig.udacity.projectone.R;
import popularmovies.one.fabianreddig.udacity.projectone.common.adapters.ListPaginatedAdapter;
import popularmovies.one.fabianreddig.udacity.projectone.common.fragments.PaginatedFragment;
import popularmovies.one.fabianreddig.udacity.projectone.databinding.FragmentMovieListBinding;
import popularmovies.one.fabianreddig.udacity.projectone.movieactivity.viewmodels.MovieListItemViewModel;
import popularmovies.one.fabianreddig.udacity.projectone.movieactivity.viewmodels.MovieListViewModel;

/**
 * Created by Fabian Reddig on 5/23/16.
 */

public class MovieListFragment extends PaginatedFragment
        implements SwipeRefreshLayout.OnRefreshListener,
        MovieListViewModel.OnLoadCompleteListener{
    private static final String TAG = MovieListFragment.class.getName();
    private static final String PAGINATE_ACTION = TAG + "_PAGINATE";

    @Inject
    LocalBroadcastManager broadcastManager;

    FragmentMovieListBinding fragmentMainListBinding;

    private MenuItem upArrowMenuItem;
    private boolean isRefreshing;

    private BroadcastReceiver onMovieClickReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            ((MovieActivity) getAppCompatActivity()).switchFragment(MovieDetailFragment.newInstance(intent.getParcelableExtra(MovieListItemViewModel.CLICKED_MOVIE)), true);
        }
    };

    public static MovieListFragment newInstance(){
        return new MovieListFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        PopularMoviesApplication.applicationComponent().inject(this);
        setHasOptionsMenu(true);
        getMovieListViewModel().attachToView();
        getMovieListViewModel().setOnLoadCompleteListener(this);

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        databindingSetup(container);
        return fragmentMainListBinding.getRoot();
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recyclerSetup();
        if(savedInstanceState==null && getMovieListViewModel().getListPosition()==0){
            refresh(false);
        }
    }

    @Override
    public void onDestroyView() {
        getMovieListViewModel().setListPosition(getFirstVisibleItem());
        super.onDestroyView();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        getMovieListViewModel().detachFromView();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_movie_list_frag, menu);

        upArrowMenuItem = menu.findItem(R.id.go_to_top);
        upArrowMenuItem.setVisible(false);

        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.go_to_top:{
                scrollToTop();
                break;
            }case R.id.popular:{
                getMovieListViewModel().setCurrentSortOrder(MovieListViewModel.POPULAR);
                refresh(false);
                break;
            }case R.id.top_rated:{
                getMovieListViewModel().setCurrentSortOrder(MovieListViewModel.TOP_RATED);
                refresh(false);
                break;
            }
        }
        return super.onOptionsItemSelected(item);
    }

//    public void showPopupMenu(View v) {
//        final PopupMenu popup = new PopupMenu(getAppCompatActivity(), v);
//        popup.setOnMenuItemClickListener(this);
//        popup.inflate(R.menu.sort_order_actions);
//        popup.show();
//    }
//
//    @Override
//    public boolean onMenuItemClick(MenuItem item) {
//        switch (item.getItemId()) {
//            case R.id.popular:
//                getMovieListViewModel().setCurrentSortOrder(MovieListViewModel.POPULAR);
//                refresh(false);
//                return true;
//            case R.id.top_rated:
//                getMovieListViewModel().setCurrentSortOrder(MovieListViewModel.TOP_RATED);
//                refresh(false);
//                return true;
//            default:
//                return false;
//        }
//    }


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
        getAppCompatActivity().setSupportActionBar(fragmentMainListBinding.toolbar);
    }

    @Override
    public void onResume() {
        super.onResume();
        broadcastManager.registerReceiver(onMovieClickReceiver, new IntentFilter(MovieListItemViewModel.ON_MOVIE_CLICKED));
    }

    @Override
    public void onPause() {
        super.onPause();
        broadcastManager.unregisterReceiver(onMovieClickReceiver);
    }

    private void recyclerSetup(){
        fragmentMainListBinding.swipeableRecyclerMain.refresh.setOnRefreshListener(this);
        fragmentMainListBinding.swipeableRecyclerMain.refresh.setProgressBackgroundColorSchemeResource(R.color.primaryRed600);
        fragmentMainListBinding.swipeableRecyclerMain.refresh.setColorSchemeResources(R.color.accentYellow400);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            fragmentMainListBinding.swipeableRecyclerMain.recycler.setOnScrollChangeListener(new MovieRecyclerOnScrollChangeListener());
        }else{
            fragmentMainListBinding.swipeableRecyclerMain.recycler.setOnScrollListener(new MovieRecyclerOnScrollListener());
        }
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
        //TODO Handle this in the UI
    }

    private void refresh(boolean swipeRefresh){
        if(!swipeRefresh){
            if(!isRefreshing){
                getMovieListViewModel().refresh();
                startRefreshing();
                paginate(page=1);
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

    private void scrollToTop(){
        fragmentMainListBinding.swipeableRecyclerMain.recycler.smoothScrollToPosition(0);
    }

    private class MovieRecyclerOnScrollListener extends RecyclerView.OnScrollListener{
        @Override
        public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
            displayUpArrowIfApplicable(getFirstVisibleItem());
        }
    }

    @TargetApi(Build.VERSION_CODES.M)
    private class MovieRecyclerOnScrollChangeListener implements View.OnScrollChangeListener {
        @Override
        public void onScrollChange(View v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
            displayUpArrowIfApplicable(getFirstVisibleItem());
        }
    }

    private int getFirstVisibleItem(){
        return ((LinearLayoutManager) fragmentMainListBinding.swipeableRecyclerMain.recycler.getLayoutManager()).findFirstVisibleItemPosition();
    }

    private void displayUpArrowIfApplicable(int firstVisibleItem){
        if(upArrowMenuItem!=null) {
            if (firstVisibleItem >= getResources().getInteger(R.integer.movie_list_arrow_offset)) {
                upArrowMenuItem.setVisible(true);
            } else if(firstVisibleItem <= getResources().getInteger(R.integer.movie_list_arrow_reset)){
                upArrowMenuItem.setVisible(false);
            }
        }
    }
}
