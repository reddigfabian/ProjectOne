package popularmovies.one.fabianreddig.udacity.projectone.dependencyinjection.components;

import javax.inject.Singleton;

import dagger.Component;
import popularmovies.one.fabianreddig.udacity.projectone.dependencyinjection.modules.ApiModule;
import popularmovies.one.fabianreddig.udacity.projectone.dependencyinjection.modules.AppModule;
import popularmovies.one.fabianreddig.udacity.projectone.movieactivity.MovieActivity;
import popularmovies.one.fabianreddig.udacity.projectone.movieactivity.MovieListFragment;
import popularmovies.one.fabianreddig.udacity.projectone.movieactivity.viewmodels.MovieListItemViewModel;

/**
 * Created by Fabian Reddig on 5/23/16.
 */

@Singleton
@Component(modules = {ApiModule.class, AppModule.class})
public interface ApplicationComponent {
    void inject(MovieActivity movieActivity);

    void inject(MovieListItemViewModel movieListItemViewModel);

    void inject(MovieListFragment movieListFragment);
}
