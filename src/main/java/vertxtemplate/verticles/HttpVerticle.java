package vertxtemplate.verticles;

import io.vertx.core.Future;
import io.vertx.core.VerticleBase;
import io.vertx.core.json.Json;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.handler.BodyHandler;
import jakarta.inject.Inject;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import vertxtemplate.configs.Config;
import vertxtemplate.controllers.AppControllers;

@Slf4j
@RequiredArgsConstructor(onConstructor_ = @Inject)
public class HttpVerticle extends VerticleBase {
    private final Config config;
    private final AppControllers appControllers;

    @Override
    public Future<?> start() {
        return vertx.createHttpServer()
                .requestHandler(buildRouter())
                .listen(config.http().port())
                .onSuccess(http -> log.info("HTTP server started on port {}", http.actualPort()));
    }

    Router buildRouter() {
        Router mainRouter = Router.router(vertx);
        configureMiddleware(mainRouter);

        Router apiRouter = Router.router(vertx);

        Router filmRouter = Router.router(vertx);
        filmRouter.get("/").handler(appControllers.getFilmController()::getAll);
        filmRouter.post("/").handler(appControllers.getFilmController()::insert);

        apiRouter.route("/films/*").subRouter(filmRouter);
        mainRouter.route("/api/*").subRouter(apiRouter);

        return mainRouter;
    }

    private void configureMiddleware(Router router) {
        // Body parsing middleware
        router.route().handler(BodyHandler.create().setBodyLimit(1024 * 1024).setHandleFileUploads(true));

        // CORS middleware
        router.route().handler(ctx -> {
            ctx.response()
                    .putHeader("content-type", "application/json")
                    .putHeader("Access-Control-Allow-Origin", "*")
                    .putHeader("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS")
                    .putHeader("Access-Control-Allow-Headers", "Content-Type, Authorization")
                    .putHeader("Access-Control-Max-Age", "86400");

            if (ctx.request().method().name().equals("OPTIONS")) {
                ctx.response().setStatusCode(204).end();
            } else {
                ctx.next();
            }
        });

        router.route().handler(ctx -> {
            String method = ctx.request().method().name();
            String path = ctx.request().uri();
            log.info("{} {}", method, path);

            long startTime = System.currentTimeMillis();
            ctx.addEndHandler(v -> {
                long duration = System.currentTimeMillis() - startTime;
                log.info(
                        "{} {} completed in {}ms with status {}",
                        method,
                        path,
                        duration,
                        ctx.response().getStatusCode());
            });

            ctx.next();
        });

        router.route().failureHandler(ctx -> {
            int statusCode = ctx.statusCode() > 0 ? ctx.statusCode() : 500;
            Throwable failure = ctx.failure();

            if (failure != null) {
                log.error("Request failed", failure);
            }

            ctx.response()
                    .setStatusCode(statusCode)
                    .end(Json.encodePrettily(
                            java.util.Map.of("error", failure != null ? failure.getMessage() : "Unknown error")));
        });
    }
}
