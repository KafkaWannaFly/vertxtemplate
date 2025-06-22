package vertxtemplate.controllers;

import io.vertx.ext.web.RoutingContext;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import vertxtemplate.models.requests.FilmCreation;
import vertxtemplate.services.IFilmService;

@Slf4j
@RequiredArgsConstructor
public class FilmController extends BaseController {
    private final IFilmService filmService;

    public void insert(RoutingContext ctx) {
        var film = this.extractAndValidateBody(ctx, FilmCreation.class);

        super.handleResponse(ctx, filmService.insert(film));
    }

    public void insertMany(RoutingContext ctx) {
        var films =
                ctx.body().asJsonArray().stream().map(FilmCreation::fromJson).toList();
        super.handleResponse(ctx, filmService.insertMany(films));
    }

    public void getById(RoutingContext ctx) {
        var id = Integer.parseInt(ctx.pathParam("id"));
        super.handleResponse(ctx, filmService.getById(id));
    }

    public void getByTitle(RoutingContext ctx) {
        var title = ctx.queryParam("title").stream().findFirst().orElse("");
        super.handleResponse(ctx, filmService.getByTitle(title));
    }
}
