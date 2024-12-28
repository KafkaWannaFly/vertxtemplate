package vertxtemplate.verticles;

import io.vertx.core.Future;
import io.vertx.core.VerticleBase;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import vertxtemplate.configs.Config;

import javax.inject.Inject;

@Slf4j
@AllArgsConstructor(onConstructor_ = @__(@Inject))
public class HttpVerticle extends VerticleBase {
    @NonNull
    private final Config config;

    @Override
    public Future<?> start() {
        return vertx.createHttpServer()
                .requestHandler(req ->
                        req.response().putHeader("content-type", "text/plain").end("Hello from Vert.x!"))
                .listen(config.http().port())
                .onSuccess(http -> log.info("HTTP server started on port {}", http.actualPort()));
    }
}
