package vertxtemplate.controllers;

import jakarta.inject.Inject;
import vertxtemplate.services.IFilmService;

public class FilmController {
    private final IFilmService filmService;

    @Inject
    public FilmController(IFilmService filmService) {
        this.filmService = filmService;
    }
}
