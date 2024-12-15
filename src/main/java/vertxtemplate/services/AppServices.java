package vertxtemplate.services;

import jakarta.inject.Inject;
import lombok.NonNull;

public record AppServices(@NonNull IFilmService filmService) {
    @Inject
    public AppServices {
    }
}
