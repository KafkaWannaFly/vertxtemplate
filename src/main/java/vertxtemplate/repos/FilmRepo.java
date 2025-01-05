package vertxtemplate.repos;

import io.vertx.core.Future;
import io.vertx.sqlclient.Pool;
import io.vertx.sqlclient.Row;
import io.vertx.sqlclient.RowSet;
import io.vertx.sqlclient.Tuple;
import jakarta.inject.Inject;
import lombok.RequiredArgsConstructor;
import vertxtemplate.models.requests.FilmCreation;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@RequiredArgsConstructor(onConstructor_ = @Inject)
public class FilmRepo implements IFilmRepo {
    private final Pool pool;

    @Override
    public Future<RowSet<Row>> insert(FilmCreation filmCreation) {
        var query =
                """
                        insert into film (title, description, release_year, length, rating)
                        values ($1, $2, $3, $4, $5)
                        """;
        return pool.preparedQuery(query)
                .execute(Tuple.of(
                        filmCreation.title(),
                        filmCreation.description(),
                        filmCreation.releaseYear(),
                        filmCreation.length(),
                        filmCreation.rating()));
    }

    @Override
    public Future<RowSet<Row>> insertMany(List<FilmCreation> films) {
        if (films.isEmpty()) {
            return Future.failedFuture("No films to insert.");
        }

        var placeholders = films.stream().map(film -> "(?, ?, ?, ?, ?)").collect(Collectors.joining(", "));

        var query = "INSERT INTO film (title, description, release_year, length, rating) VALUES " + placeholders;

        var params = films.stream()
                .flatMap(film ->
                        Stream.of(film.title(), film.description(), film.releaseYear(), film.length(), film.rating()))
                .collect(Collectors.toList());

        return pool.preparedQuery(query).execute(Tuple.from(params));
    }

    @Override
    public Future<RowSet<Row>> getAll() {
        var query = """
                select *
                from film
                """;
        return pool.query(query).execute();
    }

    @Override
    public Future<RowSet<Row>> getById(int id) {
        var query =
                """
                        select *
                        from film
                        where id = $1
                        """;
        return pool.preparedQuery(query).execute(Tuple.of(id));
    }

    @Override
    public Future<RowSet<Row>> getByTitle(String title) {
        var query =
                """
                        select *
                        from film
                        where title ilike $1
                        """;
        return pool.preparedQuery(query).execute(Tuple.of("%" + title + "%"));
    }
}
