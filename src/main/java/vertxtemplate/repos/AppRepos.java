package vertxtemplate.repos;

import jakarta.inject.Inject;

public class AppRepos {
    private final IFilmRepo filmRepo;

    @Inject
    public AppRepos(IFilmRepo filmRepo) {
        this.filmRepo = filmRepo;
    }
}
