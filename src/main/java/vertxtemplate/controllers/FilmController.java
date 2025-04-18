package vertxtemplate.controllers;

import io.vertx.ext.web.RoutingContext;
import lombok.RequiredArgsConstructor;
import vertxtemplate.models.requests.FilmCreation;
import vertxtemplate.services.IFilmService;

@RequiredArgsConstructor
public class FilmController extends BaseController {
    private final IFilmService filmService;

    public void getAll(RoutingContext ctx) {
        super.handleResponse(ctx, filmService.getAll());
    }

    public void insert(RoutingContext ctx) {
        var film = ctx.body().asJsonObject().mapTo(FilmCreation.class);
        super.handleResponse(ctx, filmService.insert(film));
    }
}
