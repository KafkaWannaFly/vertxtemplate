package vertxtemplate.controllers;

import jakarta.inject.Inject;
import vertxtemplate.services.AppServices;

public class FilmController {
    private final AppServices appServices;

    @Inject
    public FilmController(AppServices appServices) {
        this.appServices = appServices;
    }
}
