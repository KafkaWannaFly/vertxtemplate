package vertxtemplate.repos;

import jakarta.inject.Inject;

public record AppRepos(IFilmRepo filmRepo) {
    @Inject
    public AppRepos {
    }
}
