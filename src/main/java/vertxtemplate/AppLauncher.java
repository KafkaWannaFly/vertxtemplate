package vertxtemplate;

import io.vertx.core.Vertx;
import io.vertx.core.internal.logging.Logger;
import io.vertx.core.internal.logging.LoggerFactory;
import vertxtemplate.di.AppModule;
import vertxtemplate.di.DaggerAppComponent;
import vertxtemplate.verticles.HttpVerticle;

public class AppLauncher {
    private static final Logger logger = LoggerFactory.getLogger(AppLauncher.class);

    public static void main(String[] args) {
        var vertx = Vertx.vertx();
        var appComponent = DaggerAppComponent.builder()
                .appModule(new AppModule(vertx))
                .build();

        var config = appComponent.config();
        logger.info("Loaded config: " + config);

        vertx.deployVerticle(new HttpVerticle());
    }

}