package popularmovies.one.fabianreddig.udacity.projectone.common.viewmodels;

import android.databinding.ObservableArrayList;
import android.databinding.ObservableList;

import java.util.List;

import me.tatarka.bindingcollectionadapter.BaseItemViewSelector;
import me.tatarka.bindingcollectionadapter.ItemViewSelector;

public class ListModel<T> extends BaseListModel<T> {
    private ItemViewSelector<T> itemView;

    private ObservableList<T> models = new ObservableArrayList<>();

    public ListModel(BaseItemViewSelector<T> selector) {
        itemView = selector;
    }

    public ItemViewSelector<T> getItemView() {
        return itemView;
    }

    public ObservableList<T> getModels() {
        return this.models;
    }

    public void setModels(List<T> models) {
        this.models.clear();
        this.models.addAll(models);
    }

    public void addModels(List<T> models) {
        this.models.addAll(models);
    }

    @Override
    public void addModel(T model) {
        this.models.add(model);
    }

    @Override
    public void insertModel(int index, T model) {
        this.models.add(index, model);
    }

    @Override
    public void clearModels() {
        this.models.clear();
    }
}