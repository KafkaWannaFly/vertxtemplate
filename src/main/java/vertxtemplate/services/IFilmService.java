package vertxtemplate.services;

import io.vertx.core.Future;
import io.vertx.sqlclient.SqlResult;
import vertxtemplate.models.responses.Film;

import java.util.List;

public interface IFilmService {
    Future<SqlResult<List<Film>>> getAll();
}
