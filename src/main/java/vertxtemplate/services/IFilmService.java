package vertxtemplate.services;

import io.vertx.core.Future;
import vertxtemplate.models.responses.Film;

import java.util.List;

public interface IFilmService {
    Future<List<Film>> getAll();
}
