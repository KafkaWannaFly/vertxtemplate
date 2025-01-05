package vertxtemplate;

import io.vertx.core.Vertx;
import liquibase.Contexts;
import liquibase.Liquibase;
import liquibase.database.DatabaseFactory;
import liquibase.database.jvm.JdbcConnection;
import liquibase.resource.ClassLoaderResourceAccessor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import vertxtemplate.configs.Config;
import vertxtemplate.di.AppModule;
import vertxtemplate.di.DaggerIAppComponent;

import java.sql.DriverManager;

@Slf4j
public class AppLauncher {
    public static void main(String[] args) {
        var vertx = Vertx.vertx();
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            if (vertx != null) {
                log.info("Shutting down application");
                vertx.close();
            }
        }));

        try {
            var appComponent = DaggerIAppComponent.builder()
                    .appModule(new AppModule(vertx))
                    .build();

            runMigration(appComponent.config());

            vertx.deployVerticle(appComponent.httpVerticle());
        } catch (Exception e) {
            log.error("Error on starting application", e);
            vertx.close();
        }
    }

    @SneakyThrows
    private static void runMigration(Config config) {
        var connection = DriverManager.getConnection(
                config.db().url(), config.db().user(), config.db().password());
        var changeLogFile = "db/changelog/master.yaml";

        var database = DatabaseFactory.getInstance().findCorrectDatabaseImplementation(new JdbcConnection(connection));
        var liquibase = new Liquibase(changeLogFile, new ClassLoaderResourceAccessor(), database);
        liquibase.update(new Contexts());
    }
}
