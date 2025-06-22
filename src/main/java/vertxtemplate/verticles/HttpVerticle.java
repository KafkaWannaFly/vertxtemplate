package vertxtemplate.verticles;

import io.vertx.core.Future;
import io.vertx.core.VerticleBase;
import io.vertx.core.json.Json;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.handler.BodyHandler;
import jakarta.inject.Inject;
import jakarta.validation.ValidationException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import vertxtemplate.configs.Config;
import vertxtemplate.controllers.AppControllers;
import vertxtemplate.models.responses.BaseResponse;

import java.util.NoSuchElementException;
import java.util.UUID;

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
        filmRouter.get("/q").handler(appControllers.getFilmController()::getByTitle);
        filmRouter.post("/").handler(appControllers.getFilmController()::insert);
        filmRouter.post("/bulk").handler(appControllers.getFilmController()::insertMany);
        filmRouter.get("/:id").handler(appControllers.getFilmController()::getById);

        apiRouter.route("/films/*").subRouter(filmRouter);
        mainRouter.route("/api/*").subRouter(apiRouter);

        return mainRouter;
    }

    private void configureMiddleware(Router router) {
        // Request ID for logging
        router.route().handler(ctx -> {
            String requestId = UUID.randomUUID().toString();
            MDC.put("requestId", requestId);
            ctx.addEndHandler(v -> MDC.clear());
            ctx.next();
        });

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
            Throwable failure = ctx.failure();

            var statusCode =
                    switch (failure) {
                        case ValidationException ignored -> 400;
                        case NoSuchElementException ignored -> 404;
                        default -> {
                            log.error("Request failed", failure);
                            yield 500;
                        }
                    };

            ctx.response().setStatusCode(statusCode).end(Json.encodePrettily(BaseResponse.error(failure.getMessage())));
        });
    }

    @Override
    public Future<?> stop() throws Exception {
        log.info("Shutting down");
        return super.stop();
    }
}
