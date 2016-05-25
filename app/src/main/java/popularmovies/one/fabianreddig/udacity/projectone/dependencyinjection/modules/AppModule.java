package popularmovies.one.fabianreddig.udacity.projectone.dependencyinjection.modules;

import android.support.v4.content.LocalBroadcastManager;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import popularmovies.one.fabianreddig.udacity.projectone.PopularMoviesApplication;

/**
 * Created by Fabian Reddig on 5/23/16.
 */

@Module
public class AppModule {
    @Provides
    @Singleton
    public LocalBroadcastManager providesLocalBroadcastManager(){
        return LocalBroadcastManager.getInstance(PopularMoviesApplication.getApp());
    }
}
