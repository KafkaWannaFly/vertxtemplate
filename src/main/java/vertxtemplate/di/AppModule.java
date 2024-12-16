package vertxtemplate.di;

import dagger.Module;
import dagger.Provides;
import io.smallrye.config.SmallRyeConfigBuilder;
import io.vertx.core.Vertx;
import vertxtemplate.configs.Config;
import vertxtemplate.controllers.AppControllers;
import vertxtemplate.controllers.FilmController;
import vertxtemplate.repos.AppRepos;
import vertxtemplate.repos.FilmRepo;
import vertxtemplate.repos.IFilmRepo;
import vertxtemplate.services.AppServices;
import vertxtemplate.services.FilmService;
import vertxtemplate.services.IFilmService;
import vertxtemplate.verticles.HttpVerticle;

@Module
public class AppModule {
    private final Vertx vertx;

    public AppModule(Vertx vetx) {
        this.vertx = vetx;
    }

    @Provides
    HttpVerticle provideHttpVerticle(Config config) {
        return new HttpVerticle(config);
    }

    @Provides
    IFilmRepo provideFilmRepo() {
        return new FilmRepo();
    }

    @Provides
    AppRepos provideAppRepos(IFilmRepo filmRepo) {
        return new AppRepos(filmRepo);
    }

    @Provides
    IFilmService provideFilmService(AppRepos appRepos) {
        return new FilmService(appRepos);
    }

    @Provides
    AppServices provideAppServices(IFilmService filmService) {
        return new AppServices(filmService);
    }

    @Provides
    FilmController provideFilmController(AppServices appServices) {
        return new FilmController(appServices);
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
