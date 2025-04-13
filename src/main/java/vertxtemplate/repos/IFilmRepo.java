package vertxtemplate.repos;

import io.vertx.core.Future;
import io.vertx.sqlclient.Row;
import io.vertx.sqlclient.RowSet;
import io.vertx.sqlclient.SqlResult;
import vertxtemplate.models.requests.FilmCreation;
import vertxtemplate.models.responses.Film;

import java.util.List;

public interface IFilmRepo {
    Future<RowSet<Film>> insert(FilmCreation filmCreation);

    Future<RowSet<Row>> insertMany(List<FilmCreation> films);

    Future<SqlResult<List<Film>>> getAll();

    Future<RowSet<Film>> getById(int id);

    Future<RowSet<Film>> getByTitle(String title);
}
