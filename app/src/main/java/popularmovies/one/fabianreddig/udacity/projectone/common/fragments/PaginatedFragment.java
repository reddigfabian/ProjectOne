package popularmovies.one.fabianreddig.udacity.projectone.common.fragments;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;

/**
 * Created by Fabian Reddig on 05/23/2016.
 */
public abstract class PaginatedFragment extends BaseFragment {

    public int page = 0;
    private boolean paginationEnabled = true;
    private LocalBroadcastManager broadcastManager;
    private BroadcastReceiver paginator = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (isPaginationEnabled()) {
                paginate(++page);
            }
        }
    };

    public boolean isPaginationEnabled() {
        return paginationEnabled;
    }

    public void setPaginationEnabled(boolean paginationEnabled) {
        this.paginationEnabled = paginationEnabled;
    }

    public abstract String getPaginationAction();

    public abstract void paginate(int page);

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        broadcastManager = LocalBroadcastManager.getInstance(getActivity());
    }

    @Override
    public void onResume() {
        super.onResume();
        broadcastManager.registerReceiver(paginator, new IntentFilter(getPaginationAction()));
        update();
    }

    @Override
    public void onPause() {
        super.onPause();
        broadcastManager.unregisterReceiver(paginator);
    }

    public abstract void update();
}
