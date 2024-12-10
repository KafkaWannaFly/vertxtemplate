package vertxtemplate.controllers;

import jakarta.inject.Inject;

public class AppControllers {
    private final FilmController filmController;

    @Inject
    public AppControllers(FilmController filmController) {
        this.filmController = filmController;
    }
}
