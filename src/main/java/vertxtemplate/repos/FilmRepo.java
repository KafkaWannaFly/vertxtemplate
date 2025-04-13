package vertxtemplate.repos;

import io.vertx.core.Future;
import io.vertx.sqlclient.Pool;
import io.vertx.sqlclient.Row;
import io.vertx.sqlclient.RowSet;
import io.vertx.sqlclient.SqlResult;
import io.vertx.sqlclient.Tuple;
import io.vertx.sqlclient.templates.SqlTemplate;
import jakarta.inject.Inject;
import lombok.RequiredArgsConstructor;
import vertxtemplate.models.requests.FilmCreation;
import vertxtemplate.models.requests.FilmCreationParametersMapper;
import vertxtemplate.models.responses.Film;
import vertxtemplate.models.responses.FilmRowMapper;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@RequiredArgsConstructor(onConstructor_ = @Inject)
public class FilmRepo implements IFilmRepo {
    private final Pool pool;

    @Override
    public Future<RowSet<Film>> insert(FilmCreation filmCreation) {
        var query =
                """
                        insert into film (title, description, release_year, length, rating)
                        values (#{title}, #{description}, #{release_year}, #{length}, #{rating})
                        """;
        return SqlTemplate.forQuery(pool, query)
                .mapFrom(FilmCreationParametersMapper.INSTANCE)
                .mapTo(FilmRowMapper.INSTANCE)
                .execute(filmCreation);
    }

    @Override
    public Future<RowSet<Row>> insertMany(List<FilmCreation> films) {
        if (films.isEmpty()) {
            return Future.failedFuture("No films to insert.");
        }

        var placeholders = films.stream().map(film -> "(?, ?, ?, ?, ?)").collect(Collectors.joining(", "));

        var query = "INSERT INTO film (title, description, release_year, length, rating) VALUES " + placeholders;

        var params = films.stream()
                .flatMap(film -> Stream.of(
                        film.getTitle(),
                        film.getDescription(),
                        film.getReleaseYear(),
                        film.getLength(),
                        film.getRating()))
                .collect(Collectors.toList());

        return pool.preparedQuery(query).execute(Tuple.from(params));
    }

    @Override
    public Future<SqlResult<List<Film>>> getAll() {
        var query = """
                select *
                from film
                """;
        return SqlTemplate.forQuery(pool, query)
                .collecting(FilmRowMapper.COLLECTOR)
                .execute(Map.of());
    }

    @Override
    public Future<RowSet<Film>> getById(int id) {
        var query =
                """
                        select *
                        from film
                        where id = #{id}
                        """;
        return SqlTemplate.forQuery(pool, query).mapTo(FilmRowMapper.INSTANCE).execute(Map.of("id", id));
    }

    @Override
    public Future<RowSet<Film>> getByTitle(String title) {
        var query =
                """
                        select *
                        from film
                        where title ilike '%#{title}%'
                        """;
        return SqlTemplate.forQuery(pool, query).mapTo(FilmRowMapper.INSTANCE).execute(Map.of("title", title));
    }
}
