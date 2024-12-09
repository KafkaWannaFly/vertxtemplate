package vertxtemplate;

import io.vertx.config.ConfigRetriever;
import io.vertx.config.ConfigRetrieverOptions;
import io.vertx.config.ConfigStoreOptions;
import io.vertx.core.Vertx;
import io.vertx.core.internal.logging.Logger;
import io.vertx.core.internal.logging.LoggerFactory;
import io.vertx.core.json.JsonObject;
import vertxtemplate.config.Config;
import vertxtemplate.verticles.HttpVerticle;

public class AppLauncher {
    private static final Logger logger = LoggerFactory.getLogger(AppLauncher.class);

    public static void main(String[] args) {
        var vertx = Vertx.vertx();

        var config = loadConfig(vertx);
        logger.info("Loaded config: " + config);

        vertx.deployVerticle(new HttpVerticle());
    }

    static Config loadConfig(Vertx vertx) {
        var fileStore = new ConfigStoreOptions()
                .setType("file")
                .setFormat("yaml")
                .setConfig(new JsonObject().put("path", "src/main/resources/application.yaml"));

        var envStore = new ConfigStoreOptions()
                .setType("env");

        var options = new ConfigRetrieverOptions()
                .addStore(fileStore)
                .addStore(envStore);

        var retriever = ConfigRetriever.create(vertx, options);

        return retriever.getConfig().await().mapTo(Config.class);
    }
}