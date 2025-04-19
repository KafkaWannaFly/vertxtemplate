package vertxtemplate.controllers;

import io.vertx.ext.web.RoutingContext;
import jakarta.inject.Inject;
import lombok.RequiredArgsConstructor;
import vertxtemplate.services.IFilmService;

@RequiredArgsConstructor(onConstructor_ = @Inject)
public class FilmController extends BaseController {
    private final IFilmService filmService;

    public void getAll(RoutingContext ctx) {
        super.handleResponse(ctx, filmService.getAll());
    }
}
