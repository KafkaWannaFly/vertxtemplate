package vertxtemplate.controllers;

import jakarta.inject.Inject;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import vertxtemplate.services.IFilmService;

@AllArgsConstructor(onConstructor_ = @Inject)
public class FilmController {
    @NonNull
    private final IFilmService filmService;
}
