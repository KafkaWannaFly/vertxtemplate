package vertxtemplate.di;

import dagger.Module;
import dagger.Provides;
import vertxtemplate.repos.FilmRepo;
import vertxtemplate.repos.IFilmRepo;
import vertxtemplate.services.FilmService;
import vertxtemplate.services.IFilmService;

@Module
public class AppModule {
    @Provides
    IFilmRepo provideFilmRepo() {
        return new FilmRepo();
    }

    @Provides
    IFilmService provideFilmService(IFilmRepo filmRepo) {
        return new FilmService(filmRepo);
    }
}
