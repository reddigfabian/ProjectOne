package popularmovies.one.fabianreddig.udacity.projectone.dependencyinjection.modules;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import popularmovies.one.fabianreddig.udacity.projectone.api.TmdbApiWrapper;

/**
 * Created by Fabian Reddig on 5/23/16.
 */
@Module
public class ApiModule {
    @Singleton
    @Provides
    public TmdbApiWrapper providesTmdbApiWrapper(){
        return new TmdbApiWrapper();
    }
}
