package vertxtemplate.services;

import io.vertx.core.Future;
import io.vertx.sqlclient.SqlResult;
import vertxtemplate.models.requests.FilmCreation;
import vertxtemplate.models.responses.Film;

import java.util.List;

public interface IFilmService {
    Future<List<Film>> getAll();

    Future<SqlResult<Void>> insert(FilmCreation filmCreation);

    Future<Film> getById(Integer id);
}
