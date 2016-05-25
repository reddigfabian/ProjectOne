package popularmovies.one.fabianreddig.udacity.projectone.common.viewmodels;

import android.databinding.BaseObservable;
import android.databinding.ObservableList;

import java.util.List;

import me.tatarka.bindingcollectionadapter.ItemViewSelector;

/**
 * Created by WillowTree, Inc on 11/17/15.
 */
public abstract class BaseListModel<T> extends BaseObservable {
    public abstract ObservableList<T> getModels();

    public abstract void setModels(List<T> models);

    public abstract void addModels(List<T> models);

    public abstract void addModel(T model);

    public abstract void insertModel(int index, T model);

    public abstract void clearModels();

    public abstract ItemViewSelector getItemView();
}
