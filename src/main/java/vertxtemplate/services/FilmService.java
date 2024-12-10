package vertxtemplate.services;

import jakarta.inject.Inject;
import vertxtemplate.repos.AppRepos;

public class FilmService implements IFilmService {
    private final AppRepos appRepos;

    @Inject
    public FilmService(AppRepos appRepos) {
        this.appRepos = appRepos;
    }
}
