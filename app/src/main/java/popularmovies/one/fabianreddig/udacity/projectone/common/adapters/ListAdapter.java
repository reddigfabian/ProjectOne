package popularmovies.one.fabianreddig.udacity.projectone.common.adapters;

import android.support.annotation.NonNull;

import me.tatarka.bindingcollectionadapter.BindingRecyclerViewAdapter;
import me.tatarka.bindingcollectionadapter.ItemViewArg;

public class ListAdapter<T> extends BindingRecyclerViewAdapter<T> {
    /**
     * Constructs a new instance with the given {@link ItemViewArg}.
     *
     * @param arg
     */
    public ListAdapter(@NonNull ItemViewArg<T> arg) {
        super(arg);
    }
}
