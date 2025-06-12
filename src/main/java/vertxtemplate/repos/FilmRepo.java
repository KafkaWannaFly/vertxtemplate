package vertxtemplate.repos;

import io.vertx.core.Future;
import io.vertx.sqlclient.Pool;
import io.vertx.sqlclient.RowSet;
import io.vertx.sqlclient.SqlResult;
import io.vertx.sqlclient.templates.SqlTemplate;
import jakarta.inject.Inject;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import vertxtemplate.models.requests.FilmCreation;
import vertxtemplate.models.requests.FilmCreationParametersMapper;
import vertxtemplate.models.responses.Film;
import vertxtemplate.models.responses.FilmRowMapper;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor(onConstructor_ = @Inject)
public class FilmRepo implements IFilmRepo {
    private final Pool pool;

    @Override
    public Future<SqlResult<Void>> insert(FilmCreation filmCreation) {
        var query =
                """
                        insert into film (title, description, release_year, length, rating)
                        values (#{title}, #{description}, #{releaseYear}, #{length}, #{rating})
                        """;
        return SqlTemplate.forUpdate(pool, query)
                .mapFrom(FilmCreationParametersMapper.INSTANCE)
                .execute(filmCreation);
    }

    @Override
    public Future<SqlResult<Void>> insertMany(List<FilmCreation> films) {
        if (films.isEmpty()) {
            return Future.failedFuture("No films to insert.");
        }

        var query =
                """
                        insert into film (title, description, release_year, length, rating)
                        values (#{title}, #{description}, #{releaseYear}, #{length}, #{rating})
                        """;

        var paramMaps =
                films.stream().map(FilmCreationParametersMapper.INSTANCE::map).collect(Collectors.toList());

        return SqlTemplate.forUpdate(pool, query).executeBatch(paramMaps).map(batchResult -> batchResult);
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
    public Future<SqlResult<List<Film>>> getByTitle(String title) {
        var query =
                """
                        select *
                        from film
                        where title ilike #{title}
                        """;
        return SqlTemplate.forQuery(pool, query)
                .collecting(FilmRowMapper.COLLECTOR)
                .execute(Map.of("title", String.format("%%%s%%", title)));
    }
}
