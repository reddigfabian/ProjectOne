package popularmovies.one.fabianreddig.udacity.projectone.util;

import android.view.View;

import com.jakewharton.rxbinding.view.RxView;

import java.util.concurrent.TimeUnit;

import rx.Observable;
import rx.Scheduler;
import rx.Single;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

/**
 * Created by WillowTree, Inc on 9/23/15.
 */
public class RxUtil {

    public static final int TIMEOUT = 500;

    public static <T> Observable.Transformer<T, T> observableBackgroundToMainThread() {
        return observable -> observable.observeOn(AndroidSchedulers.mainThread()).subscribeOn(Schedulers.io());
    }

    public static <T> Observable.Transformer<T, T> observableBackgroundThread() {
        Scheduler thread = Schedulers.io();
        return observable -> observable.subscribeOn(thread).observeOn(thread);
    }

    public static <T> Observable.Transformer<T, T> observableMainThread() {
        return observable -> observable.observeOn(AndroidSchedulers.mainThread()).subscribeOn(AndroidSchedulers.mainThread());
    }

    public static <T> Observable.Transformer<T, T> observableParallelableToMainThread() {
        return observable -> observable.observeOn(AndroidSchedulers.mainThread()).subscribeOn(Schedulers.newThread());
    }

    public static <T> Single.Transformer<T, T> singleBackgroundToMainThread() {
        return single -> single.observeOn(AndroidSchedulers.mainThread()).subscribeOn(Schedulers.io());
    }

    public static <T> Single.Transformer<T, T> singleBackgroundThread() {
        Scheduler thread = Schedulers.io();
        return single -> single.subscribeOn(thread).observeOn(thread);
    }

    public static <T> Single.Transformer<T, T> singleMainThread() {
        return single -> single.observeOn(AndroidSchedulers.mainThread()).subscribeOn(AndroidSchedulers.mainThread());
    }

    public static <T> Single.Transformer<T, T> singleParallelableToMainThread() {
        return single -> single.observeOn(AndroidSchedulers.mainThread()).subscribeOn(Schedulers.newThread());
    }

    public static Subscription debouncedOnClick(View view, Action1<Void> action) {
        return RxView.clicks(view)
                .debounce(TIMEOUT, TimeUnit.MILLISECONDS)
                .subscribe(action);
    }
}
