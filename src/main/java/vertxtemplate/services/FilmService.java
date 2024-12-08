package vertxtemplate.services;

import jakarta.inject.Inject;
import vertxtemplate.repos.IFilmRepo;

public class FilmService implements IFilmService {
    private final IFilmRepo filmRepo;

    @Inject
    public FilmService(IFilmRepo filmRepo) {
        this.filmRepo = filmRepo;
    }
}
