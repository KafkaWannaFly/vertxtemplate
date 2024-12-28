package vertxtemplate.di;

import dagger.Module;
import dagger.Provides;
import io.smallrye.config.SmallRyeConfigBuilder;
import lombok.AllArgsConstructor;
import vertxtemplate.configs.Config;
import vertxtemplate.controllers.AppControllers;
import vertxtemplate.controllers.FilmController;
import vertxtemplate.repos.FilmRepo;
import vertxtemplate.repos.IFilmRepo;
import vertxtemplate.services.FilmService;
import vertxtemplate.services.IFilmService;
import vertxtemplate.verticles.HttpVerticle;

@Module
@AllArgsConstructor
public class AppModule {
    @Provides
    HttpVerticle provideHttpVerticle(Config config) {
        return new HttpVerticle(config);
    }

    @Provides
    IFilmRepo provideFilmRepo() {
        return new FilmRepo();
    }

    @Provides
    IFilmService provideFilmService(IFilmRepo filmRepo) {
        return new FilmService(filmRepo);
    }

    @Provides
    FilmController provideFilmController(IFilmService filmService) {
        return new FilmController(filmService);
    }

    @Provides
    AppControllers provideAppControllers(FilmController filmController) {
        return new AppControllers(filmController);
    }

    @Provides
    Config provideConfig() {
        var smallRye = new SmallRyeConfigBuilder()
                .addDiscoveredSources()
                .addSystemSources()
                .withMapping(Config.class)
                .build();

        return smallRye.getConfigMapping(Config.class);
    }
}
