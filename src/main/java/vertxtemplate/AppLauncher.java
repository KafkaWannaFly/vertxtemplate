package vertxtemplate;

import io.vertx.core.Vertx;
import io.vertx.core.internal.logging.Logger;
import io.vertx.core.internal.logging.LoggerFactory;
import org.apache.commons.lang3.ObjectUtils;
import vertxtemplate.di.AppModule;
import vertxtemplate.di.DaggerIAppComponent;

public class AppLauncher {
    private static final Logger logger = LoggerFactory.getLogger(AppLauncher.class);

    public static void main(String[] args) {
        var vertx = Vertx.vertx();
        var appComponent =
                DaggerIAppComponent.builder().appModule(new AppModule(vertx)).build();

        var controllers = appComponent.appControllers();
        if (ObjectUtils.anyNull(controllers.filmController())) {
            throw new RuntimeException("Failed to load controllers");
        }

        var services = appComponent.appServices();
        if (ObjectUtils.anyNull(services.filmService())) {
            throw new RuntimeException("Failed to load services");
        }

        var repositories = appComponent.appRepos();
        if (ObjectUtils.anyNull(repositories.filmRepo())) {
            throw new RuntimeException("Failed to load repositories");
        }

        vertx.deployVerticle(appComponent.httpVerticle());
    }
}
