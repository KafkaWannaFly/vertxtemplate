package vertxtemplate.controllers;

import jakarta.inject.Inject;
import lombok.NonNull;

public record AppControllers(@NonNull FilmController filmController) {
    @Inject
    public AppControllers {
    }
}
