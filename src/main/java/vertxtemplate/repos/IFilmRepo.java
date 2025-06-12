package vertxtemplate.repos;

import io.vertx.core.Future;
import io.vertx.sqlclient.RowSet;
import io.vertx.sqlclient.SqlResult;
import vertxtemplate.models.requests.FilmCreation;
import vertxtemplate.models.responses.Film;

import java.util.List;

public interface IFilmRepo {
    Future<SqlResult<Void>> insert(FilmCreation filmCreation);

    Future<SqlResult<Void>> insertMany(List<FilmCreation> films);

    Future<SqlResult<List<Film>>> getAll();

    Future<RowSet<Film>> getById(int id);

    Future<SqlResult<List<Film>>> getByTitle(String title);
}
