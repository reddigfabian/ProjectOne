package popularmovies.one.fabianreddig.udacity.projectone.common.adapters;

/**
 * Created by Fabian Reddig on 05/23/2016.
 */

import android.content.Intent;
import android.databinding.ViewDataBinding;
import android.support.v4.content.LocalBroadcastManager;

import me.tatarka.bindingcollectionadapter.ItemViewArg;

public class ListPaginatedAdapter<T> extends ListAdapter<T> {

    private String paginateAction;
    private boolean paginateOnScroll = true; //Default to true.

    public ListPaginatedAdapter(ItemViewArg<T> arg) {
        super(arg);
    }

    public void setPaginateOnScroll(boolean paginateOnScroll) {
        this.paginateOnScroll = paginateOnScroll;
    }

    public String getPaginateAction() {
        return paginateAction;
    }

    public void setPaginateAction(String paginateAction) {
        this.paginateAction = paginateAction;
    }

    @Override
    public void onBindBinding(ViewDataBinding binding, int bindingVariable, int layoutRes, int position, T item) {
        super.onBindBinding(binding, bindingVariable, layoutRes, position, item);
        if (position + 1 == getItemCount() && getItemCount() > 9 && paginateOnScroll) {
            LocalBroadcastManager.getInstance(binding.getRoot().getContext()).sendBroadcast(new Intent(getPaginateAction()));
        }
    }
}
