package vertxtemplate.services;

import lombok.AllArgsConstructor;
import lombok.NonNull;
import vertxtemplate.repos.AppRepos;

@AllArgsConstructor
public class FilmService implements IFilmService {
    @NonNull
    private final AppRepos appRepos;
}
