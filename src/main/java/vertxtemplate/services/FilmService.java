package vertxtemplate.services;

import jakarta.inject.Inject;
import lombok.RequiredArgsConstructor;
import vertxtemplate.repos.IFilmRepo;

@RequiredArgsConstructor(onConstructor_ = @Inject)
public class FilmService implements IFilmService {
    private final IFilmRepo filmRepo;
}
