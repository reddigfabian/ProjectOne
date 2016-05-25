package popularmovies.one.fabianreddig.udacity.projectone;

import android.app.Application;

import popularmovies.one.fabianreddig.udacity.projectone.dependencyinjection.components.ApplicationComponent;
import popularmovies.one.fabianreddig.udacity.projectone.dependencyinjection.components.DaggerApplicationComponent;
import popularmovies.one.fabianreddig.udacity.projectone.dependencyinjection.modules.ApiModule;
import popularmovies.one.fabianreddig.udacity.projectone.dependencyinjection.modules.AppModule;

/**
 * Created by Fabian Reddig on 5/23/16.
 */
public class PopularMoviesApplication extends Application{
    private static PopularMoviesApplication app;
    private ApplicationComponent applicationComponent;

    public static ApplicationComponent applicationComponent() {
        return app.applicationComponent;
    }

    public static PopularMoviesApplication getApp() {
        return app;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        app = this;
        applicationComponent = DaggerApplicationComponent.builder()
                .appModule(new AppModule())
                .apiModule(new ApiModule())
                .build();
    }
}
