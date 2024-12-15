package vertxtemplate.repos;

import jakarta.inject.Inject;
import lombok.NonNull;

public record AppRepos(@NonNull IFilmRepo filmRepo) {
    @Inject
    public AppRepos {
    }
}
