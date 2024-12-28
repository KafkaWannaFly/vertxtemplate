package vertxtemplate.services;

import jakarta.inject.Inject;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import vertxtemplate.repos.IFilmRepo;

@AllArgsConstructor(onConstructor_ = @Inject)
public class FilmService implements IFilmService {
    @NonNull
    private final IFilmRepo filmRepo;
}
