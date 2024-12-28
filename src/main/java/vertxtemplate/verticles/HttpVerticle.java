package vertxtemplate.verticles;

import io.vertx.core.Future;
import io.vertx.core.VerticleBase;
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
        assert appControllers.getFilmController() != null;

        return vertx.createHttpServer()
                .requestHandler(req ->
                        req.response().putHeader("content-type", "text/plain").end("Hello from Vert.x!"))
                .listen(config.http().port())
                .onSuccess(http -> log.info("HTTP server started on port {}", http.actualPort()));
    }
}
