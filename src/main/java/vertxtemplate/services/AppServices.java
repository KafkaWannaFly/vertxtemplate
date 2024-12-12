package vertxtemplate.services;

import jakarta.inject.Inject;

public record AppServices(IFilmService filmService) {
    @Inject
    public AppServices {
    }
}
