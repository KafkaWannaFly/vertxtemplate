package vertxtemplate.controllers;

import io.vertx.core.json.Json;
import io.vertx.ext.web.RoutingContext;
import jakarta.inject.Inject;
import lombok.RequiredArgsConstructor;
import vertxtemplate.services.IFilmService;

@RequiredArgsConstructor(onConstructor_ = @Inject)
public class FilmController {
    private final IFilmService filmService;

    public void getAll(RoutingContext ctx) {
        filmService
                .getAll()
                .onSuccess(result -> ctx.response().end(Json.encodePrettily(result.value())))
                .onFailure(err -> ctx.response()
                        .setStatusCode(500)
                        .end(Json.encodePrettily(java.util.Map.of("error", err.getMessage()))));
    }
}
