package vertxtemplate.verticles;

import io.vertx.core.Future;
import io.vertx.core.VerticleBase;
import lombok.extern.java.Log;
import vertxtemplate.configs.Config;

@Log
public class HttpVerticle extends VerticleBase {
    private final Config config;

    public HttpVerticle(Config config) {
        this.config = config;
    }

    @Override
    public Future<?> start() {
        return vertx.createHttpServer()
                .requestHandler(req ->
                        req.response().putHeader("content-type", "text/plain").end("Hello from Vert.x!"))
                .listen(config.http().port())
                .onSuccess(http -> log.info("HTTP server started on port " + http.actualPort()));
    }
}
