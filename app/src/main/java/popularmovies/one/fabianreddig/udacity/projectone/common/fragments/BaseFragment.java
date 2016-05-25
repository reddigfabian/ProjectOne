package popularmovies.one.fabianreddig.udacity.projectone.common.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;

import rx.Subscription;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by Fabian Reddig on 05/23/2016.
 */
public abstract class BaseFragment extends Fragment {
    private CompositeSubscription subscription;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        subscription = new CompositeSubscription();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        subscription.unsubscribe();
    }

    public CompositeSubscription getAllSubscriptions() {
        return this.subscription;
    }

    public void addSubscription(Subscription subscription) {
        this.subscription.add(subscription);
    }

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
