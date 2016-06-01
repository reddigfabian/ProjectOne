package popularmovies.one.fabianreddig.udacity.projectone.movieactivity;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import popularmovies.one.fabianreddig.udacity.projectone.R;
import popularmovies.one.fabianreddig.udacity.projectone.common.fragments.BaseFragment;
import popularmovies.one.fabianreddig.udacity.projectone.databinding.FragmentMovieDetailBinding;
import popularmovies.one.fabianreddig.udacity.projectone.movieactivity.viewmodels.MovieListItemViewModel;

/**
 * Created by Fabian Reddig on 5/23/16.
 */

public class MovieDetailFragment extends BaseFragment {
    private static final String TAG = MovieListFragment.class.getName();

    FragmentMovieDetailBinding fragmentMovieDetailBinding;
    private MovieListItemViewModel movieListItemViewModel;

    public static MovieDetailFragment newInstance(MovieListItemViewModel movie){
        MovieDetailFragment frag = new MovieDetailFragment();
        Bundle args = new Bundle();
        args.putParcelable(MovieListItemViewModel.CLICKED_MOVIE, movie);
        frag.setArguments(args);
        return frag;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        movieListItemViewModel = getArguments().getParcelable(MovieListItemViewModel.CLICKED_MOVIE);
        databindingSetup(container);
        return fragmentMovieDetailBinding.getRoot();
    }

    private void databindingSetup(ViewGroup container){
        fragmentMovieDetailBinding = DataBindingUtil.inflate(LayoutInflater.from(getActivity()), getLayoutId(), container, false);
        if(movieListItemViewModel!=null){
            fragmentMovieDetailBinding.setMovieViewModel(movieListItemViewModel);
        }
        fragmentMovieDetailBinding.executePendingBindings();
        getAppCompatActivity().setSupportActionBar(fragmentMovieDetailBinding.toolbar);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_movie_detail;
    }
}
