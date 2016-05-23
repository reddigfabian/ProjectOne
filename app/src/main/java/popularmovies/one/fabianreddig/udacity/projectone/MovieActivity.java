package popularmovies.one.fabianreddig.udacity.projectone;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import info.movito.themoviedbapi.TmdbApi;
import info.movito.themoviedbapi.TmdbMovies;
import info.movito.themoviedbapi.model.MovieDb;
import popularmovies.one.fabianreddig.udacity.projectone.util.RxUtil;
import rx.Observable;
import rx.Subscriber;

public class MovieActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie);
        Observable.create(new Observable.OnSubscribe<String>() {
            @Override
            public void call(Subscriber<? super String> subscriber) {
                TmdbMovies movies = new TmdbApi(BuildConfig.THE_MOVIE_DB_API_KEY).getMovies();
                MovieDb movie = movies.getMovie(550, "en");
                String title = movie.getTitle();
                subscriber.onNext(title);
            }
        }).compose(RxUtil.observableBackgroundToMainThread()).subscribe(title -> {
            Toast.makeText(getApplicationContext(), title, Toast.LENGTH_LONG).show();
        });
    }
}
