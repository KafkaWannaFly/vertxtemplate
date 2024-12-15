package vertxtemplate;

import io.vertx.core.Vertx;
import liquibase.Contexts;
import liquibase.Liquibase;
import liquibase.database.DatabaseFactory;
import liquibase.database.jvm.JdbcConnection;
import liquibase.resource.ClassLoaderResourceAccessor;
import lombok.SneakyThrows;
import lombok.extern.java.Log;
import vertxtemplate.configs.Config;
import vertxtemplate.di.AppModule;
import vertxtemplate.di.DaggerIAppComponent;

import java.sql.DriverManager;

@Log
public class AppLauncher {
    public static void main(String[] args) {
        var vertx = Vertx.vertx();
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            if (vertx != null) {
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
            log.severe(e.getMessage());
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
