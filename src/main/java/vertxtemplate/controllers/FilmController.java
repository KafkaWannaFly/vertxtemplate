package vertxtemplate.controllers;

import jakarta.inject.Inject;
import lombok.RequiredArgsConstructor;
import vertxtemplate.services.IFilmService;

@RequiredArgsConstructor(onConstructor_ = @Inject)
public class FilmController {
    private final IFilmService filmService;
}
