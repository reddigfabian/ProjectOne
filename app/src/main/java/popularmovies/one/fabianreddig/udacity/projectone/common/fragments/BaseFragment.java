package popularmovies.one.fabianreddig.udacity.projectone.common.fragments;

import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by Fabian Reddig on 05/23/2016.
 */
public abstract class BaseFragment extends Fragment {
    protected abstract int getLayoutId();

    public AppCompatActivity getAppCompatActivity() {
        return ((AppCompatActivity) getActivity());
    }

    public boolean isAlive() {
        return getActivity() != null && !getActivity().isFinishing();
    }

    public void setTitle(String title) {
        getActivity().setTitle(title);
    }

    public void setTitle(int titleId) {
        getActivity().setTitle(titleId);
    }
}
