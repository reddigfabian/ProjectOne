package popularmovies.one.fabianreddig.udacity.projectone.util;

import android.databinding.BindingAdapter;
import android.support.v4.content.ContextCompat;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import popularmovies.one.fabianreddig.udacity.projectone.R;

/**
 * Created by WillowTree, Inc. on 4/3/16.
 */
public class BindingAdapters {

    //Suppress default constructor for noninstantiability
    private BindingAdapters(){
        throw new AssertionError();
    }


    @BindingAdapter({"bind:listImageUrl"})
    public static void loadListImage(ImageView view, String url) {
        if(url!=null && !url.equals("")) {
            Picasso.with(view.getContext())
                    .load(url)
                    .resize(UiUtil.getListPosterWidth(), UiUtil.getListPosterHeight())
//                    .error(ContextCompat.getDrawable(view.getContext(), R.drawable.error)) //TODO Better error img
                    .placeholder(ContextCompat.getDrawable(view.getContext(), R.drawable.placeholder_shape)) //TODO 9Patch// placeholder
                    .into(view);
        }else{
//            view.setImageResource(R.drawable.error); // TODO: 4/9/16 Set this to a placeholder image with the movie text on top
        }
    }

    @BindingAdapter({"bind:detailImageUrl"})
    public static void loadDetailImage(ImageView view, String url) {
        if(url!=null && !url.equals("")) {
            Picasso.with(view.getContext())
                    .load(url)
                    .resize(UiUtil.getListPosterWidth(), UiUtil.getListPosterHeight())
//                    .error(ContextCompat.getDrawable(view.getContext(), R.drawable.error)) //TODO Better error img
                    .placeholder(ContextCompat.getDrawable(view.getContext(), R.drawable.placeholder_shape)) //TODO 9Patch// placeholder
                    .into(view);
        }else{
//            view.setImageResource(R.drawable.error); // TODO: 4/9/16 Set this to a placeholder image with the movie text on top
        }
    }
}