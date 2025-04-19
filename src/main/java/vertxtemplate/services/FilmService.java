package vertxtemplate.services;

import io.vertx.core.Future;
import io.vertx.sqlclient.SqlResult;
import jakarta.inject.Inject;
import lombok.RequiredArgsConstructor;
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
}
