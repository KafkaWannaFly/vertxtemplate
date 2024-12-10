package vertxtemplate.services;

import jakarta.inject.Inject;

public class AppServices {
    private final IFilmService filmService;

    @Inject
    public AppServices(IFilmService filmService) {
        this.filmService = filmService;
    }
}
