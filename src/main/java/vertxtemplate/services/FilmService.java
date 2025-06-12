package vertxtemplate.services;

import io.vertx.core.Future;
import io.vertx.sqlclient.SqlResult;
import jakarta.inject.Inject;
import lombok.RequiredArgsConstructor;
import vertxtemplate.models.requests.FilmCreation;
import vertxtemplate.models.responses.Film;
import vertxtemplate.repos.IFilmRepo;

import java.util.List;

@RequiredArgsConstructor(onConstructor_ = @Inject)
public class FilmService implements IFilmService {
    private final IFilmRepo filmRepo;

    @Override
    public Future<List<Film>> getAll() {
        return filmRepo.getAll().map(SqlResult::value);
    }

    @Override
    public Future<SqlResult<Void>> insert(FilmCreation filmCreation) {
        return filmRepo.insert(filmCreation);
    }

    @Override
    public Future<SqlResult<Void>> insertMany(List<FilmCreation> filmCreations) {
        return filmRepo.insertMany(filmCreations);
    }

    @Override
    public Future<Film> getById(Integer id) {
        return filmRepo.getById(id).map(films -> films.iterator().next());
    }

    @Override
    public Future<List<Film>> getByTitle(String title) {
        return filmRepo.getByTitle(title).map(SqlResult::value);
    }
}
