package vertxtemplate.controllers;

import jakarta.inject.Inject;

public record AppControllers(FilmController filmController) {
    @Inject
    public AppControllers {
    }
}
