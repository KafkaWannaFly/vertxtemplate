package vertxtemplate.di;

import dagger.Module;
import dagger.Provides;
import io.smallrye.config.SmallRyeConfigBuilder;
import io.vertx.core.Vertx;
import io.vertx.pgclient.PgBuilder;
import io.vertx.pgclient.PgConnectOptions;
import io.vertx.sqlclient.Pool;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import vertxtemplate.configs.Config;
import vertxtemplate.controllers.AppControllers;
import vertxtemplate.controllers.FilmController;
import vertxtemplate.repos.FilmRepo;
import vertxtemplate.repos.IFilmRepo;
import vertxtemplate.services.FilmService;
import vertxtemplate.services.IFilmService;
import vertxtemplate.verticles.HttpVerticle;

@Module
@NoArgsConstructor(force = true)
@RequiredArgsConstructor
public class AppModule {
    private final Vertx vertx;

    @Provides
    HttpVerticle provideHttpVerticle(Config config, AppControllers appControllers) {
        return new HttpVerticle(config, appControllers);
    }

    @Provides
    IFilmRepo provideFilmRepo(Pool pool) {
        return new FilmRepo(pool);
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

    @Provides
    Pool providePool(Config config) {
        var connectOptions = new PgConnectOptions()
                .setHost(config.db().host())
                .setPort(config.db().port())
                .setDatabase(config.db().name())
                .setUser(config.db().user())
                .setPassword(config.db().password());

        return PgBuilder.pool().connectingTo(connectOptions).using(this.vertx).build();
    }
}
