package vertxtemplate.repos;

import io.vertx.core.Future;
import io.vertx.sqlclient.Row;
import io.vertx.sqlclient.RowSet;
import vertxtemplate.models.requests.FilmCreation;

import java.util.List;

public interface IFilmRepo {
    Future<RowSet<Row>> insert(FilmCreation filmCreation);

    Future<RowSet<Row>> insertMany(List<FilmCreation> films);

    Future<RowSet<Row>> getAll();

    Future<RowSet<Row>> getById(int id);

    Future<RowSet<Row>> getByTitle(String title);
}
